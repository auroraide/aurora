package aurora.client.event;

import aurora.shared.backend.TreePath;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Occurs when a user clicks on a redex.
 */
public class RedexClickedEvent extends GwtEvent<RedexClickedEventHandler> {
    public static Type<RedexClickedEventHandler> TYPE = new Type<>();
    private final TreePath redex;
    // TODO in which step?

    /**
     * Constructor.
     * @param redex The redex the user clicked on.
     */
    public RedexClickedEvent(TreePath redex) {
        this.redex = redex;
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
    public TreePath getRedex() {
        return redex;
    }
}
