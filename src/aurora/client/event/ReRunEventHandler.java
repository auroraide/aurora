package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a {@link ReRunEvent}.
 */
public interface ReRunEventHandler extends EventHandler {

    /**
     * Called when the user wants re-run a calculation.
     *
     * @param event ReRun event.
     */
    void onReRun(ReRunEvent event);
}
