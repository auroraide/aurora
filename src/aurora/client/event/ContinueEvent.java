package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ContinueEvent extends GwtEvent<ContinueEventHandler> {
    public static Type<ContinueEventHandler> TYPE = new Type<>();
    @Override
    public Type<ContinueEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ContinueEventHandler continueEventHandler) {

    }
}
