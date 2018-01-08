package aurora.client.event;

import aurora.client.view.sidebar.components.strategy.StrategyType;
import com.google.gwt.event.shared.GwtEvent;

public class EvaluationStrategyChangedEvent extends GwtEvent<EvaluationStrategyChangedEventHandler> {
    public static Type<EvaluationStrategyChangedEventHandler> TYPE = new Type<>();
    private StrategyType strategyType;

    public EvaluationStrategyChangedEvent(StrategyType strategyType) {
        this.strategyType = strategyType;
    }

    @Override
    public Type<EvaluationStrategyChangedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EvaluationStrategyChangedEventHandler evaluationStrategyChangedEventHandler) {

    }
}
