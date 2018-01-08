package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class DeleteFunctionEvent extends GwtEvent<DeleteFunctionEventHandler> {
    public static Type<DeleteFunctionEventHandler> TYPE = new Type<>();

    private final String functionName;

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


    public String getFunctionName() {
        return functionName;
    }
}
