package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

/**
 * A share email all event with information.
 */
public class ShareEmailAllEvent extends GwtEvent<ShareEmailAllEventHandler> {
    public static Type<ShareEmailAllEventHandler> TYPE = new Type<>();
    private final HighlightedLambdaExpression result;
    private final List<HighlightedLambdaExpression> inputAndSteps;

    /**
     * Constructor with a result of HighlightedLambdaExpression and a list with input and steps of
     * HighlightedLambdaExpression.
     *
     * @param result        The result.
     * @param inputAndSteps The inputAndSteps.
     */
    public ShareEmailAllEvent(HighlightedLambdaExpression result, List<HighlightedLambdaExpression> inputAndSteps) {
        this.result = result;
        this.inputAndSteps = inputAndSteps;
    }

    public HighlightedLambdaExpression getResult() {
        return result;
    }

    public List<HighlightedLambdaExpression> getInputAndSteps() {
        return inputAndSteps;
    }

    @Override
    public Type<ShareEmailAllEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShareEmailAllEventHandler handler) {

    }
}
