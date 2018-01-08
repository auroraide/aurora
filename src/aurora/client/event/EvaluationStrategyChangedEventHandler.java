package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface EvaluationStrategyChangedEventHandler extends EventHandler {
    void onEvaluationStrategyChanged(EvaluationStrategyChangedEvent event);
}
