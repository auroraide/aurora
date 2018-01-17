package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

/**
 * Represents a ShareLinkAllEvent.
 */
public class ShareLinkAllEvent extends GwtEvent<ShareLinkAllEventHandler> {

    public static Type<ShareLinkAllEventHandler> TYPE = new Type<>();

    private final HighlightedLambdaExpression result;

    private final List<HighlightedLambdaExpression> inputAndSteps;

    /**
     * Constructor with HighlightedLambdaExpression and List of all steps.
     *
     * @param result        The result.
     * @param inputAndSteps The inputAndSteps.
     */
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

    /**
     * Getter for result.
     *
     * @return The result.
     */
    public HighlightedLambdaExpression getResult() {
        return result;
    }

    /**
     * Getter for input and steps.
     *
     * @return
     */
    public List<HighlightedLambdaExpression> getInputAndSteps() {
        return inputAndSteps;
    }

}
