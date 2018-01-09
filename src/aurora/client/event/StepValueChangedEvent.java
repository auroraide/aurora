package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class StepValueChangedEvent extends GwtEvent<StepValueChangedEventHandler> {
    public static Type<StepValueChangedEventHandler> TYPE = new Type<>();
    private final int stepNumber;

    /**
     * Simple constructor.
     *
     * @param stepNumber The step number from user input.
     */
    public StepValueChangedEvent(int stepNumber) {
        this.stepNumber = stepNumber;
    }


    @Override
    public Type<StepValueChangedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(StepValueChangedEventHandler handler) {

    }

    /**
     * Gets the stepNumber provided by the user as user input.
     *
     * @return step number.
     */
    public int getStepNumber() {
        return stepNumber;
    }
}
