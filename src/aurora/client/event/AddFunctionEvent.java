package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddFunctionEvent extends GwtEvent<AddFunctionEventHandler> {
    public static Type<AddFunctionEventHandler> TYPE = new Type<>();



    @Override
    public Type<AddFunctionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddFunctionEventHandler addFunctionEventHandler) {

    }
}
