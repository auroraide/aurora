package aurora.client.event;

import aurora.backend.RedexPath;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Occurs when a user clicks on a redex.
 */
public class RedexClickedEvent extends GwtEvent<RedexClickedEventHandler> {
    public static Type<RedexClickedEventHandler> TYPE = new Type<>();
    private final RedexPath redex;
    private final int stepIndex;

    /**
     * Constructor.
     * @param redex The redex the user clicked on.
     */
    public RedexClickedEvent(RedexPath redex, int stepIndex) {
        this.redex = redex;
        this.stepIndex = stepIndex;
    }

    @Override
    public Type<RedexClickedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RedexClickedEventHandler handler) {

    }

    /**
     * Gets the path pointing to an Application, which is the selected redex.
     * @return The Redex selected by the user.
     */
    public RedexPath getRedex() {
        return redex;
    }

    public int getStepIndex() {
        return stepIndex;
    }
}
