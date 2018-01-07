package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShortLinkEvent extends GwtEvent<ShortLinkEventHandler> {
    public static Type<ShortLinkEventHandler> TYPE = new Type<>();

    @Override
    public Type<ShortLinkEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShortLinkEventHandler shortLinkEventHandler) {

    }
}
