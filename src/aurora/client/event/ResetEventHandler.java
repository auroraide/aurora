package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a {@link ResetEvent}.
 */
public interface ResetEventHandler extends EventHandler {
    /**
     * Called when the user wants to reset the current run.
     * @param event Reset event.
     */
    void onReset(ResetEvent event);
}
