package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a open to new tab event.
 */
public class ToNewTabEvent extends GwtEvent<ToNewTabEventHandler> {
    public static Type<ToNewTabEventHandler> TYPE = new Type<>();
    private final int index;

    /**
     * Construct ToNewTabEvent with HighlightedLambdaExpression.
     *
     * @param index Step index. 0 means input, last index is output if it already exists.
     */
    public ToNewTabEvent(int index) {
        this.index = index;
    }

    @Override
    public Type<ToNewTabEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ToNewTabEventHandler handler) {
        handler.onToNewTab(this);
    }

    /**
     * Gets step index.
     * @return 0 is always input, last step might be output.
     */
    public int getIndex() {
        return index;
    }
}
