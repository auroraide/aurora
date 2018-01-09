package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

public class ShareEmailEvent extends GwtEvent<ShareEmailEventHandler> {
    public static Type<ShareEmailEventHandler> TYPE = new Type<>();
    private final HighlightedLambdaExpression highlightedLambdaExpression;

    /**
     * Simple constructor.
     *
     * @param highlightedLambdaExpression Highlighted lambda term of the selected input, step, or output by the user to be shared.
     */
    public ShareEmailEvent(HighlightedLambdaExpression highlightedLambdaExpression) {
        this.highlightedLambdaExpression = highlightedLambdaExpression;
    }

    @Override
    public Type<ShareEmailEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShareEmailEventHandler openEmailClientEventHandler) {

    }
}

