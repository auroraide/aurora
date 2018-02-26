package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a {@link ResultCalculatedEvent}.
 */
public interface ResultCalculatedEventHandler extends EventHandler {

    /**
     * Called when a calculation has finished.
     *
     * @param event ResultCalculatedEvent.
     */
    void onResultCalculated(ResultCalculatedEvent event);
}
