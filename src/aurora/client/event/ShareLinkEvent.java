package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.library.Library;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents the user wanting to share a short link.
 */
public class ShareLinkEvent extends GwtEvent<ShareLinkEventHandler> {
    public static Type<ShareLinkEventHandler> TYPE = new Type<>();
    private final HighlightedLambdaExpression highlightedLambdaExpression;
    private final Library userLibrary;

    /**
     * Simple constructor.
     * @param highlightedLambdaExpression The term (input, step, or output) the user has selected to be shared.
     * @param userLibrary Reference to user's current user library.
     */
    public ShareLinkEvent(HighlightedLambdaExpression highlightedLambdaExpression, Library userLibrary) {
        this.highlightedLambdaExpression = highlightedLambdaExpression;
        this.userLibrary = userLibrary;
    }

    @Override
    public Type<ShareLinkEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShareLinkEventHandler shareLinkEventHandler) {

    }

    /**
     * Gets the user's user library.
     * @return User library.
     */
    public Library getUserLibrary() {
        return userLibrary;
    }

    /**
     * Gets term the user selected for sharing.
     * @return Selected term.
     */
    public HighlightedLambdaExpression getHighlightedLambdaExpression() {
        return highlightedLambdaExpression;
    }
}
