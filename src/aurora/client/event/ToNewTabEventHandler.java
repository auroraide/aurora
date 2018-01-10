package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a {@link ToNewTabEvent}.
 */
public interface ToNewTabEventHandler extends EventHandler {
    /**
     * Called when user wants to open a new tab.
     *
     * @param toNewTabEvent The event.
     */
    void onToNewTab(ToNewTabEvent toNewTabEvent);
}
