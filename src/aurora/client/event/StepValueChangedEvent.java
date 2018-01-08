package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class StepValueChangedEvent extends GwtEvent<StepValueChangedEventHandler> {
    public static Type<StepValueChangedEventHandler> TYPE = new Type<>();
    private int newStepNumber;

    public StepValueChangedEvent(int newStepNumber) {
        this.newStepNumber = newStepNumber;
    }

    @Override
    public Type<StepValueChangedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(StepValueChangedEventHandler stepValueChangedEventHandler) {

    }

    public int getNewStepNumber() {
        return newStepNumber;
    }
}
