package aurora.client.event;

import aurora.backend.library.Library;
import com.google.gwt.event.shared.GwtEvent;

public class ShortLinkEvent extends GwtEvent<ShortLinkEventHandler> {
    public static Type<ShortLinkEventHandler> TYPE = new Type<>();
    private String rawInput;
    private Library userLibrary;

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

    public String getRawInput() {
        return rawInput;
    }

    public Library getUserLibrary() {
        return userLibrary;
    }
}
