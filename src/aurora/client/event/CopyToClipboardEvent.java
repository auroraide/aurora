package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class CopyToClipboardEvent extends GwtEvent<CopyToClipboardEventHandler> {
    public static Type<CopyToClipboardEventHandler> TYPE = new Type<>();
    private String toBeCopied;

    public CopyToClipboardEvent(String toBeCopied) {
        this.toBeCopied = toBeCopied;
    }

    @Override
    public Type<CopyToClipboardEventHandler> getAssociatedType() {
        return TYPE;
    }

    /**
     * Should only be called by {@link HandlerManager}. In other words, do not use
     * or call.
     *
     * @param handler handler
     */
    @Override
    protected void dispatch(CopyToClipboardEventHandler handler) {

    }

    public String getToBeCopied() {
        return toBeCopied;
    }
}
