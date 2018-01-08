package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;


public class CopyToClipboardEvent extends GwtEvent<CopyToClipboardEventHandler> {
    public static Type<CopyToClipboardEventHandler> TYPE = new Type<>();


    @Override
    public Type<CopyToClipboardEventHandler> getAssociatedType() {
        return TYPE;
    }


    @Override
    protected void dispatch(CopyToClipboardEventHandler handler) {
    }
}
