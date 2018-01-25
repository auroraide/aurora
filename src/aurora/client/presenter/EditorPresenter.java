package aurora.client.presenter;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.RedexPath;
import aurora.backend.betareduction.BetaReducer;
import aurora.backend.betareduction.strategies.ReductionStrategy;
import aurora.backend.library.Library;
import aurora.client.AuroraDisplay;
import aurora.client.event.StepEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;

/**
 * <code>EditorPresenter</code> is responsible for the presentation logic.
 * <p>
 <code>Aurora Presenter</code> then updates the view through
 * via the {@link AuroraDisplay}.
 */
public class EditorPresenter {
    private final AuroraDisplay auroraDisplay;

    //    private List<Step> steps;
    private final Library userLibrary;
    private final Library standardLibrary;

    private final LambdaLexer lambdaLexer;
    private final LambdaParser lambdaParser;

    private ReductionStrategy reductionStrategy;

    /**
     * Creates an <code>EditorPresenter</code> with an {@link EventBus} and a {@link AuroraDisplay}.
     *
     * @param auroraDisplay The {@link AuroraDisplay}
     */
    public EditorPresenter(AuroraDisplay auroraDisplay) {
        this.auroraDisplay = auroraDisplay;
        standardLibrary = new Library();
        userLibrary = new Library();

        bind();
        lambdaLexer = new LambdaLexer();
        lambdaParser = new LambdaParser();
    }

    private void bind() {
        eventBus.addHandler(RunEvent.TYPE, runEvent -> onRun());
        eventBus.addHandler(StepEvent.TYPE, this::onStep);
        eventBus.addHandler(ResetEvent.TYPE, this::onReset);
        eventBus.addHandler(PauseEvent.TYPE, this::onPause);
    }

    /**
     * Before entering this, we can only be in the default state.
     */
    private void onRun() {
        // view changes its state by itself.
        String input = editorDisplay.getInput();
        try {
            Term term = lambdaParser.parse(lambdaLexer.lex(input));
            HighlightedLambdaExpression hle = new HighlightableLambdaExpression(term);
            editorDisplay.setInput(hle);
            
            BetaReducer br = new BetaReducer(reductionStrategy);
            Term result = br.reduce(term);
            if (result == null) {
                // TODO display result, end
                editorDisplay.displayResult();
            } else {
                editorDisplay.addNextStep(new HighlightableLambdaExpression(result));
            }
        }
        catch (SyntaxException | SemanticException ex) {
            // TODO actually handle input errors.
            throw new RuntimeException();
        }
    }
    private void onPause(PauseEvent runEvent) {
    }
    private void onReset(ResetEvent runEvent) {
    }

    private void onStep(StepEvent stepEvent) {
        // HighlightableLambdaExpression hle = /* result of beta reduction */;
        // editorDisplay.addNextStep(hle);
    }
}
