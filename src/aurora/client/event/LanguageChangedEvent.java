package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LanguageChangedEvent extends GwtEvent<LanguageChangedEventHandler> {
    public static Type<LanguageChangedEventHandler> TYPE = new Type<>();

    @Override
    public Type<LanguageChangedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LanguageChangedEventHandler languageChangedEventHandler) {

    }
}
