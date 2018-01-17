package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Called when user wants to delete a function.
 */
public class DeleteFunctionEvent extends GwtEvent<DeleteFunctionEventHandler> {
    public static Type<DeleteFunctionEventHandler> TYPE = new Type<>();

    private final String functionName;

    /**
     * Creates a <code>DeleteFunctionEvent</code> with a function name.
     *
     * @param functionName The function name.
     */
    public DeleteFunctionEvent(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public Type<DeleteFunctionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DeleteFunctionEventHandler handler) {
    }

    /**
     * Getter for the name of the function.
     *
     * @return The function name.
     */
    public String getFunctionName() {
        return functionName;
    }
}
