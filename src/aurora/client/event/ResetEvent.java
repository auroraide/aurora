package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents the user wanting to reset the current run.
 * No data conveyed.
 */
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
