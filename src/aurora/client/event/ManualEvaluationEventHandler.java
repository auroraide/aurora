package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ManualEvaluationEventHandler extends EventHandler {
    void onManualEvaluation(ManualEvaluationEvent event);
}
