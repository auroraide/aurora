package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a open to new tab event.
 */
public class ToNewTabEvent extends GwtEvent<ToNewTabEventHandler> {
    public static Type<ToNewTabEventHandler> TYPE = new Type<>();
    private final HighlightedLambdaExpression highlightedLambdaExpression;

    /**
     * Construct ToNewTabEvent with HighlightedLambdaExpression.
     *
     * @param highlightedLambdaExpression The {@link HighlightedLambdaExpression}.
     */
    public ToNewTabEvent(HighlightedLambdaExpression highlightedLambdaExpression) {
        this.highlightedLambdaExpression = highlightedLambdaExpression;
    }

    @Override
    public Type<ToNewTabEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ToNewTabEventHandler handler) {

    }

    /**
     * Get HighlightedLambdaExpression.
     *
     * @return Return {@link HighlightedLambdaExpression}.
     */
    public HighlightedLambdaExpression getHighlightableLambdaExpression() {
        return highlightedLambdaExpression;
    }
}
