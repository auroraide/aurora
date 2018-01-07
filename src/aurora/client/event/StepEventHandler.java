package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface StepEventHandler extends EventHandler {
    void onStep(StepEvent event);
}
