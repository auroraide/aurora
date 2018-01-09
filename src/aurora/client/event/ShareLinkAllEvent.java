package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

public class ShareLinkAllEvent extends GwtEvent<ShareLinkAllEventHandler> {
    public static Type<ShareLinkAllEventHandler> TYPE = new Type<>();
    private final HighlightedLambdaExpression result;
    private final List<HighlightedLambdaExpression> inputAndSteps;

    public ShareLinkAllEvent(HighlightedLambdaExpression result, List<HighlightedLambdaExpression> inputAndSteps) {
        this.result = result;
        this.inputAndSteps = inputAndSteps;
    }

    @Override
    public Type<ShareLinkAllEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShareLinkAllEventHandler handler) {

    }

    public HighlightedLambdaExpression getResult() {
        return result;
    }

    public List<HighlightedLambdaExpression> getInputAndSteps() {
        return inputAndSteps;
    }
}
