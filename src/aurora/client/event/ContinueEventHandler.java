package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ContinueEventHandler extends EventHandler {
    /**
     * Called when the user wants to continue a currently paused run.
     * @param event Continue event.
     */
    void onContinue(ContinueEvent event);
}
