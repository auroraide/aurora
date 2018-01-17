package aurora.client.event;

import aurora.backend.parser.Token;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Occurs when a user clicks on a redex.
 */
public class RedexClickedEvent extends GwtEvent<RedexClickedEventHandler> {
    public static Type<RedexClickedEventHandler> TYPE = new Type<>();
    private final Token token;

    /**
     * Constructor.
     *
     * @param token The token of the redex the user clicked on.
     */
    public RedexClickedEvent(Token token) {
        this.token = token;
    }

    @Override
    public Type<RedexClickedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RedexClickedEventHandler handler) {

    }

    /**
     * Get the token of the redex the user clicked on.
     *
     * @return The token selected by the user.
     */
    public Token getToken() {
        return token;
    }

}
