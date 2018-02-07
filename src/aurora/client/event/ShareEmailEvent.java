package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * A share email event with information.
 */
public class ShareEmailEvent extends GwtEvent<ShareEmailEventHandler> {
    public static Type<ShareEmailEventHandler> TYPE = new Type<>();
    private final int stepIndex;

    /**
     * Create a new event with the step index to create an email with.
     * @param stepIndex Step index to share. Index 0 means input.
     */
    public ShareEmailEvent(int stepIndex) {
        this.stepIndex = stepIndex;
    }


    @Override
    public Type<ShareEmailEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShareEmailEventHandler openEmailClientEventHandler) {
        openEmailClientEventHandler.onOpenEmailClient(this);
    }

    /**
     * Step index.
     * @return Step index, 0 means input.
     */
    public int getStepIndex() {
        return stepIndex;
    }
}

