package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

/**
 * A share email all event with information.
 */
public class ShareEmailAllEvent extends GwtEvent<ShareEmailAllEventHandler> {
    public static Type<ShareEmailAllEventHandler> TYPE = new Type<>();

    @Override
    public Type<ShareEmailAllEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShareEmailAllEventHandler handler) {
        handler.onShareEmailAll(this);
    }
}
