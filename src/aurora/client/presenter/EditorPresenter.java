package aurora.client.presenter;

import aurora.backend.library.Library;
import aurora.client.EditorDisplay;
import aurora.client.event.StepEvent;
import com.google.gwt.event.shared.EventBus;

/**
 * <p>
 *     <code>EditorPresenter</code> is responsible for the presentation logic.
 * </p>
 *
 * <p>
 *     It fetches editor specific user events and acts upon those
 *     by using the backend, which presents the model. <code>Aurora Presenter</code> then updates the view through
 *     via the {@link EditorDisplay}.
 * </p>
 */
public class EditorPresenter {
    private final EventBus eventBus;
    private final EditorDisplay editorDisplay;

//    private List<Step> steps;
    private final Library userLibrary;
    private final Library standardLibrary;

    /**
     * Creates an <code>EditorPresenter</code> with an {@link EventBus} and a {@link EditorDisplay}.
     *
     * @param eventBus The event bus.
     * @param editorDisplay The {@link aurora.client.view.editor.EditorView}
     */
    public EditorPresenter(EventBus eventBus, EditorDisplay editorDisplay) {
        this.eventBus = eventBus;
        this.editorDisplay = editorDisplay;
        standardLibrary = new Library();
        userLibrary = new Library();
    }

    private void onStep(StepEvent stepEvent) {
//        HighlightedLambdaExpression hle = /* result of beta reduction */;
//        editorDisplay.addNextStep(hle);
    }
}
