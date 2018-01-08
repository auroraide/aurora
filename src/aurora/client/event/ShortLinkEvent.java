package aurora.client.event;

import aurora.backend.library.Library;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents the user wanting to share a short link.
 */
public class ShortLinkEvent extends GwtEvent<ShortLinkEventHandler> {
    public static Type<ShortLinkEventHandler> TYPE = new Type<>();
    private String rawInput;
    private Library userLibrary;

    /**
     * Simple constructor.
     * @param rawInput Raw user's input text box's contents. May be invalid.
     * @param userLibrary Reference to user's current user library.
     */
    public ShortLinkEvent(String rawInput, Library userLibrary) {
        this.rawInput = rawInput;
        this.userLibrary = userLibrary;
    }


    @Override
    public Type<ShortLinkEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShortLinkEventHandler shortLinkEventHandler) {

    }

    /**
     * Gets the raw input, as-is in the input text box. Possibly not a correct term.
     * @return User input field content.
     */
    public String getRawInput() {
        return rawInput;
    }

    /**
     * Gets the user's user library.
     * @return User library.
     */
    public Library getUserLibrary() {
        return userLibrary;
    }
}
