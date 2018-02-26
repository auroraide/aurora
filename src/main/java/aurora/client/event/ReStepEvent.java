package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ReStepEvent extends GwtEvent<ReStepEventHandler> {
    public static Type<ReStepEventHandler> TYPE = new Type<>();

    @Override
    public Type<ReStepEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ReStepEventHandler handler) {
        handler.onReStepEvent(this);
    }

}

