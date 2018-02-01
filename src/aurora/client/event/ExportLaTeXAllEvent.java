package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

import java.util.Iterator;
import java.util.List;

public class ExportLaTeXAllEvent extends GwtEvent<ExportLaTeXAllEventHandler> {
    public static Type<ExportLaTeXAllEventHandler> TYPE = new Type<>();
    private final List<HighlightedLambdaExpression> inputAndSteps;
    private final HighlightedLambdaExpression result;

    /**
     * Simple constructor.
     *
     * @param result The result.
     */
    public ExportLaTeXAllEvent(List<HighlightedLambdaExpression> inputAndSteps, HighlightedLambdaExpression result) {
        this.inputAndSteps = inputAndSteps;
        this.result = result;
    }

    @Override
    public Type<ExportLaTeXAllEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ExportLaTeXAllEventHandler handler) {
        handler.onExportLatexAll(this);
    }

    public Iterator<HighlightedLambdaExpression> getSteps() {
        return inputAndSteps.iterator();
    }

    /**
     * Can be null if no result has been calculated (yet).
     *
     * @return Return the current HighlightedLambdaExpression
     */
    public HighlightedLambdaExpression getResult() {
        return result;
    }
}

