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
import aurora.client.AuroraDisplay;
import aurora.client.event.*;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>EditorPresenter</code> is responsible for the presentation logic.
 * <p>
 <code>Aurora Presenter</code> then updates the view through
 * via the {@link AuroraDisplay}.
 */
public class EditorPresenter {
    private final AuroraDisplay auroraDisplay;

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

    private BetaReductionIterator betaReductionIterator;
    private Term last;
    private ReductionStrategy reductionStrategy;

    /**
     * Creates an <code>EditorPresenter</code> with an {@link EventBus} and a {@link AuroraDisplay}.
     *
     * @param auroraDisplay The {@link AuroraDisplay}
     */
    public EditorPresenter(EventBus eventBus, AuroraDisplay auroraDisplay) {
        this.auroraDisplay = auroraDisplay;
        standardLibrary = new Library();
        userLibrary = new Library();
        steps = new ArrayList<>();
        runTimer = new RunTimer();

        bind(eventBus);
        lambdaLexer = new LambdaLexer();
        lambdaParser = new LambdaParser();

        reset();
    }

    private void bind(EventBus eventBus) {
        eventBus.addHandler(RunEvent.TYPE, runEvent -> onRun());
        eventBus.addHandler(StepEvent.TYPE, this::onStep);
        eventBus.addHandler(ResetEvent.TYPE, runEvent1 -> onReset());
        eventBus.addHandler(PauseEvent.TYPE, runEvent -> onPause());
        eventBus.addHandler(EvaluationStrategyChangedEvent.TYPE, this::onStrategyChange);
    }

    private void reset() {
        betaReductionIterator = null;
        last = null;
        reductionStrategy = null;
    }

    private class RunTimer extends Timer {
        @Override
        public void run() {
            if (betaReductionIterator.hasNext()) {
                betaReductionIterator.next();
            }
            else {
                auroraDisplay.displayResult(new HighlightableLambdaExpression(last));
                reset();
                cancel();
            }
        }
    }

    /**
     * Before entering this, we can only be in the default state.
     * Reminder: Run doesn't show the steps, only starts evaluation in the background until completion.
     */
    private void onRun() {
        // view changes its state by itself.
        String input = auroraDisplay.getInput();

        // first up, parse the input and display it in the editor for highlighting premium.
        try {
            last = lambdaParser.parse(lambdaLexer.lex(input));
        } catch (SyntaxException | SemanticException ex) {
            // TODO actually handle input errors.
            throw new RuntimeException();
        }
        HighlightedLambdaExpression hle = new HighlightableLambdaExpression(last);
        auroraDisplay.setInput(hle);

        betaReductionIterator = new BetaReductionIterator(new BetaReducer(reductionStrategy), last);
        if (!betaReductionIterator.hasNext()) {
            // term is irreducible.
            auroraDisplay.displayResult(new HighlightableLambdaExpression(last));
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
            auroraDisplay.displayResult(new HighlightableLambdaExpression(last));
            reset();
        } else {
            // TODO display that the steps list is potentially incomplete
            for (int i = Math.min(0, steps.size() - 10); i < steps.size(); i++) {
                auroraDisplay.addNextStep(new HighlightableLambdaExpression(steps.get(i)));
            }
        }
    }
    private void onReset() {
        betaReductionIterator = null;
        runTimer.cancel();
        steps.clear();
    }

    private void onStep(StepEvent stepEvent) {
        assert (betaReductionIterator.hasNext());
        Term result = betaReductionIterator.next();
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(result);
        if (betaReductionIterator.hasNext()) {
            auroraDisplay.addNextStep(hle);
        } else {
            auroraDisplay.displayResult(hle);
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
