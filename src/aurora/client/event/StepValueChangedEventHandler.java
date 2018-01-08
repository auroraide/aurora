package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface StepValueChangedEventHandler extends EventHandler {
    void onStepValueChanged(StepValueChangedEvent event);
}
