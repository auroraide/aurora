package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ShareLinkEventHandler extends EventHandler {
    void onShareLink(ShareLinkEvent event);
}