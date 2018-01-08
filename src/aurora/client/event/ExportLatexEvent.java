package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Occurs when the user clicks the "share latex" button. Contains the term to share.
 */
public class ExportLatexEvent extends GwtEvent<ExportLatexEventHandler> {
    public static Type<ExportLatexEventHandler> TYPE = new Type<>();
    private HighlightedLambdaExpression highlightedLambdaExpression;

    /**
     * Simple constructor.
     * @param highlightedLambdaExpression The term the user has selected for exporting to LaTeX.
     */
    public ExportLatexEvent(HighlightedLambdaExpression highlightedLambdaExpression) {
        this.highlightedLambdaExpression = highlightedLambdaExpression;
    }

    @Override
    public Type<ExportLatexEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ExportLatexEventHandler exportLatexEventHandler) {

    }

    /**
     * Gets the term to be exported to LaTeX.
     * @return The term in the form the View understands.
     */
    public HighlightedLambdaExpression getHighlightedLambdaExpression() {
        return highlightedLambdaExpression;
    }
}
