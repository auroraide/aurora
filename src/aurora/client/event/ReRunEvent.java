package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents the user wanting to re-run an calculation.
 */
public class ReRunEvent extends GwtEvent<ReRunEventHandler> {
    public static Type<ReRunEventHandler> TYPE = new Type<ReRunEventHandler>();

    public Type<ReRunEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(ReRunEventHandler handler) {
        handler.onReRun(this);
    }
}
