package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a {@link ErrorDisplayedEvent}.
 */
public interface ErrorDisplayedEventHandler extends EventHandler {
    void onErrorDisplayed(ErrorDisplayedEvent event);
}
