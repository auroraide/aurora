package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface StepValueChangedEventHandler extends EventHandler {

    /**
     * Called when the user wants to set the amount of steps.
     * @param event Step event.
     */
    void onStepValueChanged(StepValueChangedEvent event);
}
