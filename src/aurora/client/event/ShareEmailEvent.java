package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.library.Library;
import com.google.gwt.event.shared.GwtEvent;

public class ShareEmailEvent extends GwtEvent<ShareEmailEventHandler> {
    public static Type<ShareEmailEventHandler> TYPE = new Type<>();
    private final HighlightedLambdaExpression highlightedLambdaExpression;
    private final Library userLibrary;

    /**
     * Simple constructor.
     *
     * @param highlightedLambdaExpression Highlighted lambda term of the selected input, step, or output by the user to be shared.
     * @param userLibrary User's current library.
     */
    public ShareEmailEvent(HighlightedLambdaExpression highlightedLambdaExpression, Library userLibrary) {
        this.highlightedLambdaExpression = highlightedLambdaExpression;
        this.userLibrary = userLibrary;
    }

    @Override
    public Type<ShareEmailEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShareEmailEventHandler openEmailClientEventHandler) {

    }

    /**
     * Gets the user's selected term to be shared.
     * @return Selected term.
     */
    public HighlightedLambdaExpression getHighlightedLambdaExpression() {
        return highlightedLambdaExpression;
    }

    /**
     * Gets the user's current user library.
     * @return User's library.
     */
    public Library getUserLibrary() {
        return userLibrary;
    }
}
