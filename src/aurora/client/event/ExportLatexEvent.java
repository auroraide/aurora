package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ExportLatexEvent extends GwtEvent<ExportLatexEventHandler> {
    public static Type<ExportLatexEventHandler> TYPE = new Type<>();
    private String lambdaTerm;

    public ExportLatexEvent(String lambdaTerm) {
        this.lambdaTerm = lambdaTerm;
    }

    @Override
    public Type<ExportLatexEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ExportLatexEventHandler exportLatexEventHandler) {

    }

    public String getLambdaTerm() {
        return lambdaTerm;
    }
}
