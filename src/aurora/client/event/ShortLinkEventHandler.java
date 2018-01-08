package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ShortLinkEventHandler extends EventHandler {
    void onShortLink(ShortLinkEvent event);
}
