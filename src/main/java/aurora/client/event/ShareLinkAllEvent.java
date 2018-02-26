package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

/**
 * Represents a ShareLinkAllEvent.
 */
public class ShareLinkAllEvent extends GwtEvent<ShareLinkAllEventHandler> {
    public static Type<ShareLinkAllEventHandler> TYPE = new Type<>();

    @Override
    public Type<ShareLinkAllEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShareLinkAllEventHandler handler) {
        handler.onShareLinkAll(this);
    }
}
