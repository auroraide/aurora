package aurora.client.presenter;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.RedexPath;
import aurora.backend.betareduction.BetaReducer;
import aurora.backend.betareduction.BetaReductionIterator;
import aurora.backend.betareduction.strategies.*;
import aurora.backend.library.Library;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.Token;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.simplifier.ChurchNumberSimplifier;
import aurora.backend.simplifier.LibraryTermSimplifier;
import aurora.backend.tree.Term;
import aurora.client.EditorDisplay;
import aurora.client.event.ContinueEvent;
import aurora.client.event.EvaluationStrategyChangedEvent;
import aurora.client.event.PauseEvent;
import aurora.client.event.ReStepEvent;
import aurora.client.event.RedexClickedEvent;
import aurora.client.event.ResetEvent;
import aurora.client.event.RunEvent;
import aurora.client.event.StepEvent;
import aurora.client.event.StepValueChangedEvent;
import aurora.client.view.sidebar.strategy.StrategyType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <code>EditorPresenter</code> is responsible for the presentation logic.
 * <p>
 * It fetches editor specific user events and acts upon those
 * by using the backend, which presents the model. <code>Aurora Presenter</code> then updates the view through
 * via the {@link EditorDisplay}.
 */
public class EditorPresenter {
    private final EventBus eventBus;
    private final EditorDisplay editorDisplay;

    private final Library userLibrary;
    private final Library standardLibrary;

    private final ChurchNumberSimplifier churchNumberSimplifier;
    private final LibraryTermSimplifier libraryTermSimplifier;

    private final LambdaLexer lambdaLexer;
    private final LambdaParser lambdaParser;

    /**
     * Contains all steps in between, so without input and without output fields.
     */
    private final ArrayList<Term> steps;
    private final HighlightTimer highlightTimer;
    /**
     * GWT Timer, allows for "while" loops without blocking the GUI.
     * This will be null when not running, and not null when running.
     */
    private RunTimer runTimer;
    private int stepNumber;
    private StrategyType reductionStrategy;

    private Iterator<Term> reStepper;
    private Integer nextReStepIndex;

    /**
     * Creates an <code>EditorPresenter</code> with the given components.
     *
     * @param eventBus      The event bus.
     * @param editorDisplay The {@link EditorDisplay}
     * @param lambdaLexer   The lexer to use.
     * @param lambdaParser  The parser to use.
     */
    public EditorPresenter(
            EventBus eventBus,
            EditorDisplay editorDisplay,
            Library standardLibrary,
            Library userLibrary,
            ChurchNumberSimplifier churchNumberSimplifier,
            LibraryTermSimplifier libraryTermSimplifier,
            ArrayList<Term> steps,
            LambdaLexer lambdaLexer,
            LambdaParser lambdaParser) {
        this.editorDisplay = editorDisplay;
        this.eventBus = eventBus;
        this.standardLibrary = standardLibrary;
        this.userLibrary = userLibrary;
        this.churchNumberSimplifier = churchNumberSimplifier;
        this.libraryTermSimplifier = libraryTermSimplifier;
        this.steps = steps;
        this.lambdaLexer = lambdaLexer;
        this.lambdaParser = lambdaParser;

        highlightTimer = new HighlightTimer();
        runTimer = null;
        reStepper = null;
        stepNumber = 1;

        bind();

        reset();
    }

    private void bind() {
        eventBus.addHandler(RunEvent.TYPE, runEvent -> onRun());
        eventBus.addHandler(StepEvent.TYPE, stepEvent -> onStep());
        eventBus.addHandler(ResetEvent.TYPE, runEvent -> onReset());
        eventBus.addHandler(PauseEvent.TYPE, pauseEvent -> onPause());
        eventBus.addHandler(EvaluationStrategyChangedEvent.TYPE, this::onStrategyChange);
        /* TODO this doesn't compile, fix me
        eventBus.addHandler(RedexClickedEvent.TYPE, redexClickedEvent
                -> onRedexClicked(redexClickedEvent.getToken()));
         */
        eventBus.addHandler(ContinueEvent.TYPE, e -> onContinue());

        eventBus.addHandler(ReStepEvent.TYPE, e -> onReStep());

        eventBus.addHandler(StepValueChangedEvent.TYPE, e -> stepNumber = e.getStepNumber());
    }



    private void reset() {
        if (runTimer != null) {
            runTimer.cancel();
            runTimer = null;
        }

        reductionStrategy = StrategyType.NORMALORDER;
        highlightTimer.scheduleRepeating(1000);
        editorDisplay.resetSteps();
        editorDisplay.resetResult();
        reStepper = null;
        nextReStepIndex = null;
        steps.clear();
    }

    private void finish() {
        runTimer.cancel();
        runTimer = null;
    }

    private Term last() {
        assert (!steps.isEmpty());
        return steps.get(steps.size() - 1);
    }

    private Term simplify(Term t) {
        Term simplified = null;

        if ((simplified = this.churchNumberSimplifier.simplify(t)) != null) {
            return simplified;
        }
        if ((simplified = this.libraryTermSimplifier.simplify(t)) != null) {
            return simplified;
        }

        return t;
    }

    private boolean isRunning() {
        return runTimer != null;
    }

    private boolean isStarted() {
        return !steps.isEmpty();
    }

    private boolean isReStepping() {
        // assert (!isRunning() && isStarted());
        assert ((nextReStepIndex == null) == (reStepper == null));
        return reStepper != null;
    }

    private ReductionStrategy createReductionStrategy() {
        switch (reductionStrategy) {
            case CALLBYVALUE:
                return new CallByValue();
            case CALLBYNAME:
                return new CallByName();
            case NORMALORDER:
                return new NormalOrder();
            default:
                throw new IllegalStateException("Unknown strategy type");
        }
    }


    private void onContinue() {
        GWT.log("EP: ContinueEvent caught.");
        assert (!isRunning() && isStarted() && !isReStepping());
        assert (reductionStrategy != StrategyType.MANUALSELECTION);
        editorDisplay.resetSteps();
        runTimer = new RunTimer(new BetaReductionIterator(new BetaReducer(createReductionStrategy()), last()));
        runTimer.scheduleRepeating(1);
    }

    private void onPause() {
        GWT.log("EP: PauseEvent caught.");
        assert (isRunning() && isStarted() && !isReStepping());

        runTimer.cancel();
        runTimer = null;

        List<HighlightedLambdaExpression> highlightedSteps = new ArrayList<>();
        for (int i = Math.max(0, steps.size() - stepNumber); i < steps.size(); i++) {
            highlightedSteps.add(new HighlightableLambdaExpression(steps.get(i)));
        }
        editorDisplay.addNextStep(highlightedSteps, steps.size() - stepNumber);
    }

    private void onReset() {
        GWT.log("EP: ResetEvent caught.");
        reset();
    }

    /**
     * Gets everything ready and adds the input to steps.
     * Sends Syntax/Semantic errors to the editorDisplay.
     *
     * @return false if syntax/semantic errors.
     */
    private boolean tryStartOrHandleErrors() {
        assert (steps.isEmpty());

        highlightTimer.cancel();

        // lex input
        List<Token> stream = lexInputOrHandleErrors();
        if (stream == null) {
            return false;
        }

        // parse input
        Term term = parseInputOrHandleErrors(stream);
        if (term == null) {
            return false;
        }

        steps.add(term);

        // use original token stream to preserve exact input with whitespaces and stuff
        editorDisplay.setInput(new HighlightableLambdaExpression(stream));

        return true;
    }

    private void onStep() {
        GWT.log("EP: StepEvent caught.");
        assert (reductionStrategy != StrategyType.MANUALSELECTION);
        assert (!isRunning() && !isReStepping());

        if (!isStarted()) {
            if (!tryStartOrHandleErrors()) {
                return;
            }
        }

        BetaReductionIterator bri = new BetaReductionIterator(new BetaReducer(createReductionStrategy()), last());
        ArrayList<HighlightedLambdaExpression> stepsToDisplay = new ArrayList<>();

        // is input reducible?
        if (!bri.hasNext()) {
            editorDisplay.displayResult(new HighlightableLambdaExpression(simplify(last())));
            assert (last() == steps.get(0));
            return;
        }

        // at this point steps.size() == 1.

        for (int i = 0; i < stepNumber; i++) {
            Term result = bri.next();
            steps.add(result);
            if (!bri.hasNext()) {
                // current is irreducible => current term is result.
                editorDisplay.displayResult(new HighlightableLambdaExpression(simplify(result)));
                break;
            } else {
                stepsToDisplay.add(new HighlightableLambdaExpression(result));
            }
        }

        int stepsSize = steps.size();
        /*
         * In the case of stepNumber being greater than the actual size of steps,
         * that can be displayed, we should set it to index 1.
         */
        int stepIndex = (stepsSize - stepNumber > 0) ? stepsSize -  stepNumber : 1;
        editorDisplay.addNextStep(stepsToDisplay, stepIndex);
    }

    private void onReStep() {
        if (!isReStepping()) {
            // if not yet restepping, initialize.
            reStepper = steps.iterator();
            reStepper.next();
            nextReStepIndex = 1;
            editorDisplay.resetSteps(); // this SHOULD not be necessary, but doesn't hurt anyway
        }

        GWT.log("EP: ReStepEvent caught.");
        assert (reductionStrategy != StrategyType.MANUALSELECTION);

        ArrayList<HighlightedLambdaExpression> ssss = new ArrayList<>(stepNumber);

        if (!reStepper.hasNext()) {
            editorDisplay.finishedFinished(new HighlightableLambdaExpression(steps.get(0)));
            return;
        }

        for (int i = 0; i < stepNumber; i++) {
            Term current = reStepper.next();

            if (!reStepper.hasNext()) {
                // current is irreducible => current term is result.
                editorDisplay.finishedFinished(new HighlightableLambdaExpression(current));
                break;
            } else {
                ssss.add(new HighlightableLambdaExpression(current));
            }
        }

        editorDisplay.addNextStep(ssss, nextReStepIndex);
        nextReStepIndex += stepNumber;
    }
    
    private void onRedexClicked(HighlightedLambdaExpression.Redex redex) {
        GWT.log("EP: RedexClickEvent caught.");
        assert (!isRunning() && isStarted());

        RedexPath path = redex.redex;
        ReductionStrategy strategy = new UserStrategy(path);


    }

    private void onStrategyChange(EvaluationStrategyChangedEvent strat) {
        assert (runTimer == null);
        reductionStrategy = strat.getStrategyType();
    }

    /**
     * Tries to parse the given token stream calls the appropriate stuff in case it's wrong.
     *
     * @return Term tree or null on bad input.
     */
    private Term parseInputOrHandleErrors(List<Token> stream) {
        Term t;
        try {
            t = lambdaParser.parse(stream);
        } catch (SemanticException e) {
            editorDisplay.displaySemanticError(e);
            return null;
        } catch (SyntaxException e) {
            editorDisplay.displaySyntaxError(e);
            return null;
        }

        return t;
    }

    /**
     * Tries to lex the user input and calls the appropriate stuff in case it's wrong.
     *
     * @return Token stream or null on bad input.
     */
    private List<Token> lexInputOrHandleErrors() {
        String input = editorDisplay.getInput();

        // lex input
        List<Token> stream = null;
        try {
            stream = lambdaLexer.lex(input);
        } catch (SyntaxException e) {
            editorDisplay.displaySyntaxError(e);
            return null;
        }

        return stream;
    }

    /**
     * Before entering this, we can only be in the default state.
     * Reminder: Run doesn't show the steps, only starts evaluation in the background until completion.
     */
    private void onRun() {
        GWT.log("EP: RunEvent caught.");
        assert (!isRunning() && !isStarted() && !isReStepping());
        assert (reductionStrategy != StrategyType.MANUALSELECTION);

        if (!tryStartOrHandleErrors()) {
            return;
        }

        BetaReductionIterator betaReductionIterator =
                new BetaReductionIterator(new BetaReducer(createReductionStrategy()), last());

        if (!betaReductionIterator.hasNext()) {
            editorDisplay.displayResult(new HighlightableLambdaExpression(simplify(last())));
            return;
        }

        runTimer = new RunTimer(betaReductionIterator);
        runTimer.scheduleRepeating(1);
    }

    private class RunTimer extends Timer {
        private final BetaReductionIterator betaReductionIterator;

        private RunTimer(BetaReductionIterator betaReductionIterator) {
            this.betaReductionIterator = betaReductionIterator;
        }

        @Override
        public void run() {
            // we have already been started by our onRun function.
            assert (this.betaReductionIterator != null);
            assert (this.betaReductionIterator.hasNext());

            Term current = betaReductionIterator.next();
            steps.add(current);

            if (!betaReductionIterator.hasNext()) {
                // current is irreducible => result.
                editorDisplay.displayResult(new HighlightableLambdaExpression(simplify(current)));
                finish();
            }
        }
    }

    private class HighlightTimer extends Timer {
        @Override
        public void run() {
           /* Term t = parseInputOrHandleErrors();
            if (t == null) {
                return;
            }
            HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
            // editorDisplay.setInput(hle); // TODO re-enable this once we have working semantic highlighting*/
        }
    }
}
