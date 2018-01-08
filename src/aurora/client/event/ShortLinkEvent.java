package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.library.Library;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents the user wanting to share a short link.
 */
public class ShortLinkEvent extends GwtEvent<ShortLinkEventHandler> {
    public static Type<ShortLinkEventHandler> TYPE = new Type<>();
    private final HighlightedLambdaExpression highlightedLambdaExpression;
    private final Library userLibrary;

    /**
     * Simple constructor.
     * @param highlightedLambdaExpression The term (input, step, or output) the user has selected to be shared.
     * @param userLibrary Reference to user's current user library.
     */
    public ShortLinkEvent(HighlightedLambdaExpression highlightedLambdaExpression, Library userLibrary) {
        this.highlightedLambdaExpression = highlightedLambdaExpression;
        this.userLibrary = userLibrary;
    }

    @Override
    public Type<ShortLinkEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShortLinkEventHandler shortLinkEventHandler) {

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
