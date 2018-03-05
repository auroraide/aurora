package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Called, when an error is displayed.
 */
public class ErrorDisplayedEvent extends GwtEvent<ErrorDisplayedEventHandler> {
    public static Type<ErrorDisplayedEventHandler> TYPE = new Type<ErrorDisplayedEventHandler>();

    @Override
    public Type<ErrorDisplayedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ErrorDisplayedEventHandler handler) {
        handler.onErrorDisplayed(this);
    }
}
