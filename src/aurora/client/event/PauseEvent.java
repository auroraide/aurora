package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class PauseEvent extends GwtEvent<PauseEventHandler> {
    public static Type<PauseEventHandler> TYPE = new Type<>();

    @Override
    public Type<PauseEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PauseEventHandler pauseEventHandler) {

    }
}
