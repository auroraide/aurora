package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

public class ToNewTabEvent extends GwtEvent<ToNewTabEventHandler> {
    public static Type<ToNewTabEventHandler> TYPE = new Type<>();
    private final HighlightedLambdaExpression highlightedLambdaExpression;

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

    public HighlightedLambdaExpression getHighlightedLambdaExpression() {
        return highlightedLambdaExpression;
    }
}
