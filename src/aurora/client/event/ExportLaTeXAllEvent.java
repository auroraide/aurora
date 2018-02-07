package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

import java.util.Iterator;
import java.util.List;

public class ExportLaTeXAllEvent extends GwtEvent<ExportLaTeXAllEventHandler> {
    public static Type<ExportLaTeXAllEventHandler> TYPE = new Type<>();

    /**
     * Simple constructor.
     */
    public ExportLaTeXAllEvent(List<HighlightedLambdaExpression> inputAndSteps) {
    }

    @Override
    public Type<ExportLaTeXAllEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ExportLaTeXAllEventHandler handler) {
        handler.onExportLatexAll(this);
    }
}

