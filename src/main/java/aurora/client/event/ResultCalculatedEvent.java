package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Fired on a finished calculation.
 */
public class ResultCalculatedEvent extends GwtEvent<ResultCalculatedEventHandler> {
    public static Type<ResultCalculatedEventHandler> TYPE = new Type<ResultCalculatedEventHandler>();

    @Override
    public Type<ResultCalculatedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ResultCalculatedEventHandler handler) {
        handler.onResultCalculated(this);
    }
}
