package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class RunEvent extends GwtEvent<RunEventHandler> {
    public static Type<RunEventHandler> TYPE = new Type<>();

    @Override
    public Type<RunEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RunEventHandler runEventHandler) {

    }
}
