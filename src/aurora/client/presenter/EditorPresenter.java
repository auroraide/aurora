package aurora.client.presenter;

import aurora.client.EditorDisplay;
import aurora.client.event.StepValueChangedEvent;
import com.google.gwt.event.shared.EventBus;

public class EditorPresenter {
    private final EventBus eventBus;
    private final EditorDisplay editorDisplay;

    public EditorPresenter(EventBus eventBus, EditorDisplay editorView) {
        this.eventBus = eventBus;
        this.editorDisplay = editorView;
    }

    private void onStepValueChanged(StepValueChangedEvent stepValueChangedEvent) {
        editorDisplay.setStepNumber(stepValueChangedEvent.getStepNumber());
    }
}
