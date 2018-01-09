package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ToNewTabEventHandler extends EventHandler {
    void onToNewTab(ToNewTabEvent toNewTabEvent);
}
