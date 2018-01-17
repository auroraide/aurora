package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a {@link ShareLinkEvent}.
 */
public interface ShareLinkEventHandler extends EventHandler {
    /**
     * Called when the user wants to share a link.
     *
     * @param event The event.
     */
    void onShareLink(ShareLinkEvent event);
}
