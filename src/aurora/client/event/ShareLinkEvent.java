package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents the user wanting to share a short link.
 */
public class ShareLinkEvent extends GwtEvent<ShareLinkEventHandler> {
    public static Type<ShareLinkEventHandler> TYPE = new Type<>();
    /**
     * Represents the index of the result step.
     */
    public static final int RESULT = -1;

    /**
     * Represents the index of input.
     */
    public static final int INPUT = 0;
    private final int step;

    /**
     * Simple constructor.
     * @param step Step index. 0 is input. -1 is result.
     */
    public ShareLinkEvent(int step) {
        this.step = step;
    }

    @Override
    public Type<ShareLinkEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShareLinkEventHandler shareLinkEventHandler) {
        shareLinkEventHandler.onShareLink(this);
    }

    /**
     * Get step index.
     * @return step index, 0 means input and -1 means result.
     */
    public int getStep() {
        return step;
    }
}

