package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a {@link RunEvent}.
 */
public interface RunEventHandler extends EventHandler {
    /**
     * Called when the user wants to start a new run.
     *
     * @param event Run event.
     */
    void onRun(RunEvent event);
}
