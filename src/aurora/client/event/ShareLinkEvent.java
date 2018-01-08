package aurora.client.event;

import aurora.shared.backend.library.Library;
import com.google.gwt.event.shared.GwtEvent;

public class ShareLinkEvent extends GwtEvent<ShareLinkEventHandler> {
    public static Type<ShareLinkEventHandler> TYPE = new Type<>();
    private String rawInput;
    private Library userLibrary;

    public ShortLinkEvent(String rawInput, Library userLibrary) {
        this.rawInput = rawInput;
        this.userLibrary = userLibrary;
    }


    @Override
    public Type<ShareLinkEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShareLinkEventHandler shareLinkEventHandler) {

    }

    public String getRawInput() {
        return rawInput;
    }

    public Library getUserLibrary() {
        return userLibrary;
    }
}
