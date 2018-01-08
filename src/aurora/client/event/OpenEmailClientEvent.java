package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class OpenEmailClientEvent extends GwtEvent<OpenEmailClientEventHandler> {
    public static Type<OpenEmailClientEventHandler> TYPE = new Type<>();
    private String url;

    public OpenEmailClientEvent(String url) {
        this.url = url;
    }

    @Override
    public Type<OpenEmailClientEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(OpenEmailClientEventHandler openEmailClientEventHandler) {

    }

    public String getUrl() {
        return url;
    }
}
