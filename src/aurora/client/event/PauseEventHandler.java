package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface PauseEventHandler extends EventHandler {
    void onPause(PauseEvent event);
}
