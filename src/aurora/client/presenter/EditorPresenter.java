package aurora.client.presenter;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.betareduction.BetaReducer;
import aurora.backend.betareduction.BetaReductionIterator;
import aurora.backend.betareduction.strategies.*;
import aurora.backend.library.Library;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.Term;
import aurora.client.EditorDisplay;
import aurora.client.event.*;
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
    private final List<Term> steps;

    /**
     * GWT Timer, allows for "while" loops without blocking the GUI.
     */
    private final RunTimer runTimer;
    private final HighlightTimer highlightTimer;

    private BetaReductionIterator betaReductionIterator;
    private Term last;
    private ReductionStrategy reductionStrategy;

    /**
     * Creates an <code>EditorPresenter</code> with an {@link EventBus} and a {@link EditorDisplay}.
     *  @param eventBus      The event bus.
     * @param editorDisplay The {@link EditorDisplay}
     */
    public EditorPresenter(EventBus eventBus, EditorDisplay editorDisplay) {
        this.editorDisplay = editorDisplay;
        this.eventBus = eventBus;

        standardLibrary = new Library();
        userLibrary = new Library();
        steps = new ArrayList<>();
        lambdaLexer = new LambdaLexer();
        lambdaParser = new LambdaParser();

        runTimer = new RunTimer();
        highlightTimer = new HighlightTimer();

        bind();

        reset();
    }

    private void bind() {
        eventBus.addHandler(RunEvent.TYPE, runEvent -> onRun());
        eventBus.addHandler(StepEvent.TYPE, stepEvent -> onStep());
        eventBus.addHandler(ResetEvent.TYPE, runEvent -> onReset());
        eventBus.addHandler(PauseEvent.TYPE, pauseEvent -> onPause());
        eventBus.addHandler(EvaluationStrategyChangedEvent.TYPE, this::onStrategyChange);
    }

    private void reset() {
        betaReductionIterator = null;
        last = null;
        reductionStrategy = null;
        highlightTimer.scheduleRepeating(1000);
    }

    private class RunTimer extends Timer {
        @Override
        public void run() {
            assert(betaReductionIterator != null);
            assert(last != null);

            if (betaReductionIterator.hasNext()) {
                betaReductionIterator.next();
            }
            else {
                editorDisplay.displayResult(new HighlightableLambdaExpression(last));
                reset();
                cancel();
            }
        }
    }

    private class HighlightTimer extends Timer {
        @Override
        public void run() {
            assert(betaReductionIterator == null);
            String input = editorDisplay.getInput();
            Term t;
            try {
                t = lambdaParser.parse(lambdaLexer.lex(input));
            } catch (SemanticException | SyntaxException e) {
                throw new RuntimeException("Not implemented");
            }

            HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
            editorDisplay.setInput(hle);
        }
    }

    /**
     * Before entering this, we can only be in the default state.
     * Reminder: Run doesn't show the steps, only starts evaluation in the background until completion.
     */
    private void onRun() {
        // view changes its state by itself.
        String input = editorDisplay.getInput();

        // first up, parse the input and display it in the editor for highlighting premium.
        try {
            last = lambdaParser.parse(lambdaLexer.lex(input));
        } catch (SyntaxException | SemanticException ex) {
            // TODO actually handle input errors.
            throw new RuntimeException("Not implemented");
        }
        HighlightedLambdaExpression hle = new HighlightableLambdaExpression(last);
        editorDisplay.setInput(hle);

        betaReductionIterator = new BetaReductionIterator(new BetaReducer(reductionStrategy), last);
        if (!betaReductionIterator.hasNext()) {
            // term is irreducible.
            editorDisplay.displayResult(new HighlightableLambdaExpression(last));
        }
        else {
            runTimer.scheduleRepeating(0);
        }
    }
    private void onPause() {
        assert (runTimer != null);
        runTimer.cancel();
        if (!betaReductionIterator.hasNext()) {
            // this is a corner case, and in most cases won't happen.
            editorDisplay.displayResult(new HighlightableLambdaExpression(last));
            reset();
        } else {
            // TODO display that the steps list is potentially incomplete
            for (int i = Math.min(0, steps.size() - 10); i < steps.size(); i++) {
                editorDisplay.addNextStep(new HighlightableLambdaExpression(steps.get(i)));
            }
        }
    }
    private void onReset() {
        betaReductionIterator = null;
        runTimer.cancel();
        steps.clear();
    }

    private void onStep() {
        assert (betaReductionIterator.hasNext());
        Term result = betaReductionIterator.next();
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(result);
        if (betaReductionIterator.hasNext()) {
            editorDisplay.addNextStep(hle);
        } else {
            editorDisplay.displayResult(hle);
        }
    }

    private void onStrategyChange(EvaluationStrategyChangedEvent strat) {
        switch (strat.getStrategyType()) {

            case CALLBYVALUE:
                reductionStrategy = new CallByValue();
                break;
            case CALLBYNAME:
                reductionStrategy = new CallByName();
                break;
            case NORMALORDER:
                reductionStrategy = new NormalOrder();
                break;
            case MANUALSELECTION:
                throw new RuntimeException(); // TODO implement UserStrategy
//                reductionStrategy = new UserStrategy();
        }

    }
}
