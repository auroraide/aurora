package aurora.client.presenter;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.RedexPath;
import aurora.backend.betareduction.BetaReducer;
import aurora.backend.betareduction.BetaReductionIterator;
import aurora.backend.betareduction.strategies.CallByName;
import aurora.backend.betareduction.strategies.CallByValue;
import aurora.backend.betareduction.strategies.NormalOrder;
import aurora.backend.betareduction.strategies.ReductionStrategy;
import aurora.backend.betareduction.strategies.UserStrategy;
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
    private BetaReductionIterator berry;

    /**
     * Contains all steps in between, so without input and without output fields.
     */
    private final ArrayList<Step> steps;

    private final HighlightTimer highlightTimer;
    private RunTimer runTimer;
    private StepTimer stepTimer;

    private ReStepTimer reStepTimer;
    private Iterator<Step> reStepper;
    private Integer nextReStepIndex;

    private int stepsToComputeAtOnce;
    private StrategyType reductionStrategy;

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
            ArrayList<Step> steps,
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
        this.reductionStrategy = StrategyType.NORMALORDER;
        highlightTimer = new HighlightTimer();
        runTimer = null;
        reStepper = null;
        stepsToComputeAtOnce = 1;

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

        eventBus.addHandler(StepValueChangedEvent.TYPE, e -> stepsToComputeAtOnce = e.getStepNumber());
    }



    private void reset() {
        if (runTimer != null) {
            runTimer.cancel();
            runTimer = null;
        }
        if (stepTimer != null) {
            stepTimer.cancel();
            stepTimer = null;
        }
        highlightTimer.scheduleRepeating(100);
        editorDisplay.resetSteps();
        editorDisplay.resetResult();
        reStepper = null;
        nextReStepIndex = null;
        berry = null;
        steps.clear();
    }

    private void finish() {
        if (runTimer != null) {
            runTimer.cancel();
            runTimer = null;
        }
    }

    private Step last() {
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

    private boolean isStepping() {
        return stepTimer != null;
    }

    private boolean isReStepping() {
        // assert (!isRunning() && isStarted());
        // assert ((nextReStepIndex == null) == (reStepper == null));
        return reStepper != null;
    }

    private ReductionStrategy createReductionStrategy() {
        switch (reductionStrategy) {
            case CALLBYVALUE:
                GWT.log("CBV detected");
                return new CallByValue();
            case CALLBYNAME:
                GWT.log("CBN detected");
                return new CallByName();
            case NORMALORDER:
                GWT.log("Normalorder detected");
                return new NormalOrder();
            default:
                throw new IllegalStateException("Unknown strategy type");
        }
    }

    private void onContinue() {
        GWT.log("EP: ContinueEvent caught.");
        if (isRunning() || !isStarted() || isReStepping()) {
            return;
        }
        if (reductionStrategy == StrategyType.MANUALSELECTION) {
            return;
        }

        if (isStepping()) {
            stepTimer.cancel();
            stepTimer = null;
        }

        berry = new BetaReductionIterator(new BetaReducer(createReductionStrategy()), last().getTerm());
        if (!berry.hasNext()) {
            editorDisplay.displayResult(new HighlightableLambdaExpression(simplify(last().getTerm())));
            finish();
            return;
        }
        runTimer = new RunTimer();
        runTimer.scheduleRepeating(1);
    }

    private void onPause() {
        GWT.log("EP: PauseEvent caught.");
        if (!isStarted()) {
            return;
        }
        if (isReStepping()) {
            return;
        }

        if (isRunning()) {
            runTimer.cancel();
            runTimer = null;
        } else if (isStepping()) {
            stepTimer.cancel();
            stepTimer = null;
        } else {
            return;
        }

        editorDisplay.resetSteps();

        editorDisplay.addNextStep(last().getHle(), steps.size() - 1);
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
        highlightTimer.cancel();

        berry = new BetaReductionIterator(new BetaReducer(createReductionStrategy()), term);

        // is input reducible?
        if (berry.hasNext()) {
            // input is reducible => there exists at least one redex.
            HighlightableLambdaExpression hle
                    = new HighlightableLambdaExpression(stream, term, berry.getSelectedRedex());
            steps.add(new Step(term, hle));
            editorDisplay.setInput(hle);
        } else {
            // input (step #0) is NOT reducible => display result

            HighlightableLambdaExpression hleInput = new HighlightableLambdaExpression(stream);
            steps.add(new Step(term, hleInput));
            editorDisplay.setInput(hleInput);

            Term simplifiedInput = simplify(term);
            HighlightableLambdaExpression hle = new HighlightableLambdaExpression(simplifiedInput);
            steps.add(new Step(simplifiedInput, hle));
            editorDisplay.displayResult(hle);

            assert (steps.size() == 2); // contains only input and output
            return false;
        }

        return true;
    }

    private void onStep() {
        GWT.log("EP: StepEvent caught.");
        if (reductionStrategy == StrategyType.MANUALSELECTION) {
            return;
        }
        if (isRunning() || isReStepping() || isStepping()) {
            return;
        }

        if (!isStarted()) {
            if (!tryStartOrHandleErrors()) {
                return;
            }
        }

        if (!berry.hasNext()) {
            editorDisplay.displayResult(new HighlightableLambdaExpression(simplify(last().getTerm())));
            finish();
            return;
        }

        stepTimer = new StepTimer();
        stepTimer.scheduleRepeating(1);
    }

    private class StepTimer extends Timer {
        private int counter;

        public StepTimer() {
            counter = stepsToComputeAtOnce;
        }

        @Override
        public void run() {
            if (counter-- <= 0) {
                cancel();
                stepTimer = null;
                return;
            }

            Term next = berry.next();
            HighlightableLambdaExpression hle = new HighlightableLambdaExpression(next, berry.getSelectedRedex());
            steps.add(new Step(next, hle));


            if (!berry.hasNext()) {
                cancel();
                stepTimer = null; // works because GC
                editorDisplay.displayResult(hle);
                return;
            }

            editorDisplay.addNextStep(hle, steps.size() - 1);
        }
    }

    private void onReStep() {
        GWT.log("EP: ReStepEvent caught.");
        if (reductionStrategy == StrategyType.MANUALSELECTION) {
            return;
        }

        if (!isReStepping()) {
            // if not yet restepping, initialize.
            reStepper = steps.iterator();
            reStepper.next(); // skip input
            nextReStepIndex = 1;
            editorDisplay.resetSteps(); // this SHOULD not be necessary, but doesn't hurt anyway
        }

        if (!reStepper.hasNext()) {
            editorDisplay.finishedFinished(steps.get(steps.size() - 1).getHle());
            return;
        }

        reStepTimer = new ReStepTimer();
        reStepTimer.scheduleRepeating(1);

    }

    private class ReStepTimer extends Timer {
        private int counter;

        private ReStepTimer() {
            this.counter = stepsToComputeAtOnce;
        }

        @Override
        public void run() {
            if (!reStepper.hasNext()) {
                return;
            }

            if (counter-- <= 0) {
                cancel();
                reStepTimer = null;
                return;
            }

            Step current = reStepper.next();

            if (reStepper.hasNext()) {
                // current reducible
                editorDisplay.addNextStep(current.getHle(), nextReStepIndex);
            } else {
                // current is irreducible => current term is result.
                editorDisplay.finishedFinished(current.getHle());
            }

            nextReStepIndex++;
        }
    }

    private void onRedexClicked(HighlightedLambdaExpression.Redex redex) {
        GWT.log("EP: RedexClickEvent caught.");
        if (isRunning() || !isStarted()) {
            RedexPath path = redex.redex;
            ReductionStrategy strategy = new UserStrategy(path);
        }
    }

    private void onStrategyChange(EvaluationStrategyChangedEvent strat) {
        if (runTimer != null) {
            return;
        }
        reductionStrategy = strat.getStrategyType();
        if (berry != null) {
            berry = new BetaReductionIterator(new BetaReducer(createReductionStrategy()), last().getTerm());
        }
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
        List<Token> stream;
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
        if (isRunning() || isStarted() || isReStepping()) {
            return;
        }
        if (reductionStrategy == StrategyType.MANUALSELECTION) {
            return;
        }

        if (!tryStartOrHandleErrors()) {
            return;
        }

        assert (steps.size() == 1); // only the input is at index 0
        assert (berry != null);
        runTimer = new RunTimer();
        runTimer.scheduleRepeating(1);
    }

    private class RunTimer extends Timer {

        @Override
        public void run() {
            // we have already been started by our onRun function.
            assert (berry != null);
            assert (berry.hasNext());

            Term current = berry.next();
            if (!berry.hasNext()) {
                // current is irreducible => result.
                editorDisplay.displayResult(new HighlightableLambdaExpression(simplify(current)));
                finish();
            }

            steps.add(new Step(current, new HighlightableLambdaExpression(current, berry.getSelectedRedex())));
        }
    }

    private class HighlightTimer extends Timer {

        private String lastInput = "";
        private StrategyType lastStrategy = null;

        @Override
        public void run() {
            String input = editorDisplay.getInput();

            // skip if input unchanged
            if (lastInput.equals(input) && lastStrategy == reductionStrategy) {
                return;
            }

            // cache last input string
            lastInput = input;
            lastStrategy = reductionStrategy;
            editorDisplay.deletem();
            // attempt lex
            List<Token> stream = null;
            try {
                stream = lambdaLexer.lex(input);
            } catch (SyntaxException e) {
                // skip
                return;
            }

            // attempt parse
            Term term = null;
            try {
                term = lambdaParser.parse(stream);
            } catch (SyntaxException | SemanticException e) {
                return;
            }

            // display highlighted input
            HighlightedLambdaExpression hle = new HighlightableLambdaExpression(stream, term,
                    createReductionStrategy().getRedexPath(term));
            editorDisplay.setInput(hle);
        }
    }

}
