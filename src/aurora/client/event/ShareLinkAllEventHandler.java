package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a {@link ShareLinkAllEvent}.
 */
public interface ShareLinkAllEventHandler extends EventHandler {
    /**
     * Called when the user wants to share a link.
     *
     * @param event The event.
     */
    void onShareLinkAll(ShareLinkAllEvent event);
}
