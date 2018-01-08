package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ManualEvaluationEvent extends GwtEvent<ManualEvaluationEventHandler> {
    public static Type<ManualEvaluationEventHandler> TYPE = new Type<>();
    private String lambdaTerm;

    public ManualEvaluationEvent(String lambdaTerm) {
        this.lambdaTerm = lambdaTerm;
    }

    @Override
    public Type<ManualEvaluationEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ManualEvaluationEventHandler manualEvaluationEventHandler) {

    }

    public String getLambdaTerm() {
        return lambdaTerm;
    }
}
