package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface EvaluationStrategyChangedEventHandler extends EventHandler {
    /**
     * Called when the user selects a different evaluation strategy.
     * @param event Evaluation strategy changed event.
     */
    void onEvaluationStrategyChanged(EvaluationStrategyChangedEvent event);
}
