package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a {@link PauseEvent}.
 */
public interface PauseEventHandler extends EventHandler {
    /**
     * Called when the user wants to pause the current run.
     * @param event Pause event.
     */
    void onPause(PauseEvent event);
}
