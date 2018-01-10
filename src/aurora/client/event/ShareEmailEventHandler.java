package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles {@link ShareEmailEvent}
 */
public interface ShareEmailEventHandler extends EventHandler {
    /**
     * Called when user wants to share something via the eMail client.
     *
     * @param event The event.
     */
    void onOpenEmailClient(ShareEmailEvent event);
}
