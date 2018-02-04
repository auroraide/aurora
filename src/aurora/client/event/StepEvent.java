package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents the user wanting to perform a step. The information of how many steps to take can be obtained
 * by the StepValueChangedEvent and it should be stored in the presenter.
 */
public class StepEvent extends GwtEvent<StepEventHandler> {
    public static Type<StepEventHandler> TYPE = new Type<>();

    @Override
    public Type<StepEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(StepEventHandler stepEventHandler) {
        stepEventHandler.onStep(this);
    }
}
