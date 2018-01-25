package aurora.client.presenter;

import aurora.backend.library.Library;
import aurora.client.AuroraDisplay;
import aurora.client.event.StepEvent;
import com.google.gwt.event.shared.EventBus;

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

    /**
     * Creates an <code>EditorPresenter</code> with an {@link EventBus} and a {@link AuroraDisplay}.
     *
     * @param auroraDisplay The {@link AuroraDisplay}
     */
    public EditorPresenter(AuroraDisplay auroraDisplay) {
        this.auroraDisplay = auroraDisplay;
        standardLibrary = new Library();
        userLibrary = new Library();
    }

    private void onStep(StepEvent stepEvent) {
        // HighlightableLambdaExpression hle = /* result of beta reduction */;
        // editorDisplay.addNextStep(hle);
    }
}
