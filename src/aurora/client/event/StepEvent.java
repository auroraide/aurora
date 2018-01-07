package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class StepEvent extends GwtEvent<StepEventHandler> {
    public static Type<StepEventHandler> TYPE = new Type<>();

    @Override
    public Type<StepEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(StepEventHandler stepEventHandler) {

    }
}
