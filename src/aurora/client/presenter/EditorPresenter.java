package aurora.client.presenter;

import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.library.Library;
import aurora.client.EditorDisplay;
import aurora.client.event.StepEvent;
import aurora.client.event.StepValueChangedEvent;
import com.google.gwt.event.shared.EventBus;

public class EditorPresenter {
    private final EventBus eventBus;
    private final EditorDisplay editorDisplay;

//    private List<Step> steps;
    private final Library userLibrary;
    private final Library standardLibrary;

    public EditorPresenter(EventBus eventBus, EditorDisplay editorView) {
        this.eventBus = eventBus;
        this.editorDisplay = editorView;
        standardLibrary = new Library();
        userLibrary = new Library();
    }

    private void onStepValueChanged(StepValueChangedEvent stepValueChangedEvent) {
        editorDisplay.setStepNumber(stepValueChangedEvent.getStepNumber());
    }

    private void onStep(StepEvent stepEvent) {
        HighlightedLambdaExpression hle = /* result of beta reduction */;
        editorDisplay.addNextStep(hle);
    }
}
