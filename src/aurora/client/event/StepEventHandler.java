package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface StepEventHandler extends EventHandler {
    /**
     * Called when the user wants to perform a fixed amount of steps.
     * @param event Step event.
     */
    void onStep(StepEvent event);
}
