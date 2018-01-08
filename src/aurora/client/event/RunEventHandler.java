package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface RunEventHandler extends EventHandler {
    /**
     * Called when the user wants to start a new run.
     * @param event Run event.
     */
    void onRun(RunEvent event);
}
