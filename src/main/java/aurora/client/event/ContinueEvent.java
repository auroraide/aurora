package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents the user wanting to continue a currently paused run.
 * No data conveyed.
 */
public class ContinueEvent extends GwtEvent<ContinueEventHandler> {
    public static Type<ContinueEventHandler> TYPE = new Type<>();

    @Override
    public Type<ContinueEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ContinueEventHandler continueEventHandler) {
        continueEventHandler.onContinue(this);
    }

}
