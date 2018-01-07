package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ManualEvaluationEvent extends GwtEvent<ManualEvaluationEventHandler> {
    public static Type<ManualEvaluationEventHandler> TYPE = new Type<>();

    @Override
    public Type<ManualEvaluationEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ManualEvaluationEventHandler manualEvaluationEventHandler) {

    }
}
