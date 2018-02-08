package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class FinishFinishEvent extends GwtEvent<FinishFinishEventHandler> {
    public static Type<FinishFinishEventHandler> TYPE = new Type<FinishFinishEventHandler>();

    public Type<FinishFinishEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(FinishFinishEventHandler handler) {
        handler.onFinishFinish(this);
    }
}
