package aurora.client.presenter;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.betareduction.BetaReducer;
import aurora.backend.betareduction.BetaReductionIterator;
import aurora.backend.betareduction.strategies.*;
import aurora.backend.library.Library;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.Token;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.Term;
import aurora.client.EditorDisplay;
import aurora.client.event.*;
import aurora.client.view.sidebar.strategy.StrategyType;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;

import java.util.ArrayList;
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

    private final LambdaLexer lambdaLexer;
    private final LambdaParser lambdaParser;

    /**
     * Contains all steps in between, so without input and without output fields.
     */
    private final ArrayList<Term> steps;

    /**
     * GWT Timer, allows for "while" loops without blocking the GUI.
     * This will be null when not running, and not null when running.
     */
    private RunTimer runTimer;
    private final HighlightTimer highlightTimer;

    private StrategyType reductionStrategy;

    /**
     * Creates an <code>EditorPresenter</code> with the given components.
     *
     * @param eventBus      The event bus.
     * @param editorDisplay The {@link EditorDisplay}
     * @param lambdaLexer   The lexer to use.
     * @param lambdaParser  The parser to use.
     */
    public EditorPresenter(EventBus eventBus, EditorDisplay editorDisplay, LambdaLexer lambdaLexer, LambdaParser lambdaParser) {
        this.editorDisplay = editorDisplay;
        this.eventBus = eventBus;
        this.lambdaLexer = lambdaLexer;
        this.lambdaParser = lambdaParser;

        standardLibrary = new Library();
        userLibrary = new Library();
        steps = new ArrayList<>();

        highlightTimer = new HighlightTimer();

        bind();

        reset();
    }

    /**
     * Creates an <code>EditorPresenter</code> with an {@link EventBus} and a {@link EditorDisplay}.
     *
     * @param eventBus      The event bus.
     * @param editorDisplay The {@link EditorDisplay}
     */
    public EditorPresenter(EventBus eventBus, EditorDisplay editorDisplay) {
        this(eventBus, editorDisplay, new LambdaLexer(), new LambdaParser());
    }

    private void bind() {
        eventBus.addHandler(RunEvent.TYPE, runEvent -> onRun());
        eventBus.addHandler(StepEvent.TYPE, stepEvent -> onStep(stepEvent.getStepCount()));
        eventBus.addHandler(ResetEvent.TYPE, runEvent -> onReset());
        eventBus.addHandler(PauseEvent.TYPE, pauseEvent -> onPause());
        eventBus.addHandler(EvaluationStrategyChangedEvent.TYPE, this::onStrategyChange);
        eventBus.addHandler(RedexClickedEvent.TYPE, redexClickedEvent
                -> onRedexClicked(redexClickedEvent.getToken()));
    }

    private void reset() {
        reductionStrategy = StrategyType.NORMALORDER;
        highlightTimer.scheduleRepeating(1000);
        steps.clear();
    }

    private void finish() {
        runTimer.cancel();
        runTimer = null;
        editorDisplay.displayResult(new HighlightableLambdaExpression(last()));
    }

    private Term last() {
        assert (!steps.isEmpty());
        return steps.get(steps.size() - 1);
    }

    private boolean isRunning() {
        return runTimer != null;
    }
    private boolean isStarted() {
        return !steps.isEmpty();
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

    /**
     * Before entering this, we can only be in the default state.
     * Reminder: Run doesn't show the steps, only starts evaluation in the background until completion.
     */
    private void onRun() {
        assert(!isRunning() && !isStarted());

        if (!tryStartOrHandleErrors()) {
            return;
        }

        BetaReductionIterator betaReductionIterator =
                new BetaReductionIterator(new BetaReducer(createReductionStrategy()), last());
        runTimer = new RunTimer(betaReductionIterator);
        runTimer.scheduleRepeating(0);
    }

    private void onContinue() {
        assert(!isRunning() && isStarted());
        assert(reductionStrategy != StrategyType.MANUALSELECTION);
        // TODO hide steps?
        runTimer = new RunTimer(new BetaReductionIterator(new BetaReducer(createReductionStrategy()), last()));
        runTimer.scheduleRepeating(0);
    }

    private void onPause() {
        assert (isRunning() && isStarted());

        runTimer.cancel();
        runTimer = null;

        // TODO make it apparent to the user that the steps list is potentially incomplete
        List<HighlightedLambdaExpression> highlightedSteps = new ArrayList<>();
        for (int i = Math.max(0, steps.size() - 10); i < steps.size(); i++) {
            highlightedSteps.add(new HighlightableLambdaExpression(steps.get(i)));
        }
        editorDisplay.addNextStep(highlightedSteps);
    }

    private void onReset() {
        reset();
    }

    /**
     * Gets everything ready and adds the input to steps.
     * Sends Syntax/Semantic errors to the editorDisplay.
     * @return false if syntax/semantic errors.
     */
    private boolean tryStartOrHandleErrors() {
        assert(steps.isEmpty());

        highlightTimer.cancel();
        Term input = parseInputOrHandleErrors();
        if (input == null) {
            return false;
        }
        steps.add(input);
        HighlightedLambdaExpression hle = new HighlightableLambdaExpression(input);
        editorDisplay.setInput(hle);

        return true;
    }

    private void onStep(int stepCount) {
        assert(reductionStrategy != StrategyType.MANUALSELECTION);
        assert(!isRunning());

        if (!isStarted()) {
            if (!tryStartOrHandleErrors()) {
                return;
            }
        }

        BetaReductionIterator bri = new BetaReductionIterator(new BetaReducer(createReductionStrategy()), last());
        if (bri.hasNext()) {
            Term result = bri.next();
            steps.add(result);
            HighlightableLambdaExpression hle = new HighlightableLambdaExpression(result);
            if (!bri.hasNext()) {
                // the result of the input is irreducible.
                editorDisplay.displayResult(hle);
            } else {
                // the most common case
                ArrayList<HighlightedLambdaExpression> step = new ArrayList<>();
                step.add(hle);
                editorDisplay.addNextStep(step);
            }
        } else {
            // input term is irreducible already.
            HighlightedLambdaExpression hle = new HighlightableLambdaExpression(last());
            editorDisplay.displayResult(hle);
        }
    }

    private void onRedexClicked(Token token) {
        assert(!isRunning() && isStarted());
        // todo impl
    }

    private void onStrategyChange(EvaluationStrategyChangedEvent strat) {
        assert(runTimer == null);
        reductionStrategy = strat.getStrategyType();
    }

    private class RunTimer extends Timer {
        private final BetaReductionIterator betaReductionIterator;

        private RunTimer(BetaReductionIterator betaReductionIterator) {
            this.betaReductionIterator = betaReductionIterator;
        }

        @Override
        public void run() {
            assert (this.betaReductionIterator != null);
            assert (this.betaReductionIterator.hasNext());

            steps.add(this.betaReductionIterator.next());

            if (!this.betaReductionIterator.hasNext()) {
                editorDisplay.displayResult(new HighlightableLambdaExpression(last()));
                finish();
            }
        }
    }

    /**
     * Tries to parse the user input and calls the appropriate stuff in case it's wrong.
     * @return Input or null on bad input.
     */
    private Term parseInputOrHandleErrors() {
        String input = editorDisplay.getInput();
        Term t;
        try {
            t = lambdaParser.parse(lambdaLexer.lex(input));
        } catch (SemanticException e) {
            editorDisplay.displaySemanticError(e);
            return null;
        } catch (SyntaxException e) {
            editorDisplay.displaySyntaxError(e);
            return null;
        }
        return t;
    }

    private class HighlightTimer extends Timer {
        @Override
        public void run() {
            Term t = parseInputOrHandleErrors();
            if (t == null) {
                return;
            }
            HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
            editorDisplay.setInput(hle);
        }
    }
}
