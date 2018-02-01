package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents the user wanting to pause the current run.
 * No data conveyed.
 */
public class PauseEvent extends GwtEvent<PauseEventHandler> {
    public static Type<PauseEventHandler> TYPE = new Type<>();

    @Override
    public Type<PauseEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PauseEventHandler pauseEventHandler) {
        pauseEventHandler.onPause(this);
    }

}
