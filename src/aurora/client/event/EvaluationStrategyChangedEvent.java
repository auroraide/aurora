package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EvaluationStrategyChangedEvent extends GwtEvent<EvaluationStrategyChangedEventHandler> {
    public static Type<EvaluationStrategyChangedEventHandler> TYPE = new Type<>();
    @Override
    public Type<EvaluationStrategyChangedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EvaluationStrategyChangedEventHandler evaluationStrategyChangedEventHandler) {

    }
}
