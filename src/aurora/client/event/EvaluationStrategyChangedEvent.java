package aurora.client.event;

import aurora.client.view.sidebar.strategy.StrategyType;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents the user selecting a different evaluation strategy.
 */
public class EvaluationStrategyChangedEvent extends GwtEvent<EvaluationStrategyChangedEventHandler> {
    public static Type<EvaluationStrategyChangedEventHandler> TYPE = new Type<>();
    private final StrategyType strategyType;

    /**
     * Constructor with {@link StrategyType}.
     *
     * @param strategyType The {@link StrategyType}.
     */
    public EvaluationStrategyChangedEvent(StrategyType strategyType) {
        this.strategyType = strategyType;
    }

    @Override
    public Type<EvaluationStrategyChangedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EvaluationStrategyChangedEventHandler evaluationStrategyChangedEventHandler) {
        evaluationStrategyChangedEventHandler.onEvaluationStrategyChanged(this);
    }

    /**
     * Gets the evaluation strategy type selected by the user.
     *
     * @return Strategy.
     */
    public StrategyType getStrategyType() {
        return strategyType;
    }
}

