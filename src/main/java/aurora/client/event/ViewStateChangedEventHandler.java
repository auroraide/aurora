package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a {@link ViewStateChangedEvent}.
 */
public interface ViewStateChangedEventHandler extends EventHandler {

    /**
     * Called when the view is about to change its state.
     */
    void onViewStateChanged(ViewStateChangedEvent viewStateChangedEvent);
}
