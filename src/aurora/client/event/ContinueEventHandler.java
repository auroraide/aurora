package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a {@link ContinueEventHandler}
 */
public interface ContinueEventHandler extends EventHandler {
    /**
     * Called when user wants to resume a paused calculation.
     *
     * @param event User input.
     */
    void onContinue(ContinueEvent event);
}
