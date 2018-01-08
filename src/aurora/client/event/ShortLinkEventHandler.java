package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ShortLinkEventHandler extends EventHandler {
    /**
     * Called when user wants to generate and share a short link to their input.
     * @param event Event.
     */
    void onShortLink(ShortLinkEvent event);
}
