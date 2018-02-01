package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Occurs when the user changes the langauge.
 */
public class LanguageChangedEvent extends GwtEvent<LanguageChangedEventHandler> {
    public static Type<LanguageChangedEventHandler> TYPE = new Type<>();
    // TODO language field?

    @Override
    public Type<LanguageChangedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LanguageChangedEventHandler languageChangedEventHandler) {
        languageChangedEventHandler.onLanguageChanged(this);
    }
}
