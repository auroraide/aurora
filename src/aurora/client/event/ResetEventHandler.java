package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ResetEventHandler extends EventHandler {
    void onReset(ResetEvent event);
}
