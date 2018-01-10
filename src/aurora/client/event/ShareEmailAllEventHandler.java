package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a {@link ShareEmailAllEvent}.
 */
public interface ShareEmailAllEventHandler extends EventHandler {
    /**
     * Called when user wants to share lambda term from input, steps and result.
     *
     * @param shareEmailAllEvent The event.
     */
    void onShareEmailAll(ShareEmailAllEvent shareEmailAllEvent);
}
