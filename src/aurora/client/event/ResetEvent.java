package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ResetEvent extends GwtEvent<ResetEventHandler> {
    public static Type<ResetEventHandler> TYPE = new Type<>();

    @Override
    public Type<ResetEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ResetEventHandler resetEventHandler) {

    }
}
