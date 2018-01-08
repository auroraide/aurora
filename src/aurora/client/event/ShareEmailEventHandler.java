package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ShareEmailEventHandler extends EventHandler {
    void onOpenEmailClient(ShareEmailEvent event);
}
