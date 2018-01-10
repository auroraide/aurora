package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a {@link LanguageChangedEvent}.
 */
public interface LanguageChangedEventHandler extends EventHandler {
    /**
     * Called when user changes langauge.
     * @param event Language change event.
     */
    void onLanguageChanged(LanguageChangedEvent event);
}
