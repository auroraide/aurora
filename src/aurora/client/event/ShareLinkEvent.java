package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents the user wanting to share a short link.
 */
public class ShareLinkEvent extends GwtEvent<ShareLinkEventHandler> {
    public static Type<ShareLinkEventHandler> TYPE = new Type<>();
    private final HighlightedLambdaExpression highlightedLambdaExpression;

    /**
     * Simple constructor.
     * @param highlightedLambdaExpression The term (input, step, or output) the user has selected to be shared.
     */
    public ShareLinkEvent(HighlightedLambdaExpression highlightedLambdaExpression) {
        this.highlightedLambdaExpression = highlightedLambdaExpression;
    }

    @Override
    public Type<ShareLinkEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShareLinkEventHandler shareLinkEventHandler) {

    }

    /**
     * Gets term the user selected for sharing.
     * @return Selected term.
     */
    public HighlightedLambdaExpression getHighlightableLambdaExpression() {
        return highlightedLambdaExpression;
    }


}

