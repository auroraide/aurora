package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents the user wanting to perform a fixed amount of steps.
 */
public class StepEvent extends GwtEvent<StepEventHandler> {
    public static Type<StepEventHandler> TYPE = new Type<>();
    private final int stepCount;

    /**
     * Simple constructor.
     * @param stepCount Amount of steps to perform, greater or equals 1.
     */
    public StepEvent(int stepCount) {
        this.stepCount = stepCount;
    }

    @Override
    public Type<StepEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(StepEventHandler stepEventHandler) {

    }

    /**
     * Gets the amount of steps the user has selected to be evaluated.
     * @return Amount of steps greater or equals 1.
     */
    public int getStepCount() {
        return stepCount;
    }
}
