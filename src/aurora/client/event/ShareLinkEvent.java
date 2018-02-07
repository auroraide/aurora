package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents the user wanting to share a short link.
 */
public class ShareLinkEvent extends GwtEvent<ShareLinkEventHandler> {
    public static Type<ShareLinkEventHandler> TYPE = new Type<>();
    private final int step;

    /**
     * Simple constructor.
     * @param step Step index. 0 is input.
     */
    public ShareLinkEvent(int step) {
        this.step = step;
    }

    @Override
    public Type<ShareLinkEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShareLinkEventHandler shareLinkEventHandler) {
        shareLinkEventHandler.onShareLink(this);
    }

    /**
     * Get step index.
     * @return step index, 0 means input.
     */
    public int getStep() {
        return step;
    }
}

