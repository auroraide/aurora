package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Occurs when the user clicks the "share LaTeX" button. Contains the term to share.
 */
public class ExportLaTeXEvent extends GwtEvent<ExportLaTeXEventHandler> {
    public static Type<ExportLaTeXEventHandler> TYPE = new Type<>();
    private final HighlightedLambdaExpression highlightedLambdaExpression;

    /**
     * Simple constructor.
     *
     * @param highlightedLambdaExpression The term the user has selected for exporting to LaTeX.
     */
    public ExportLaTeXEvent(HighlightedLambdaExpression highlightedLambdaExpression) {
        this.highlightedLambdaExpression = highlightedLambdaExpression;
    }

    @Override
    public Type<ExportLaTeXEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ExportLaTeXEventHandler exportLaTeXEventHandler) {
        exportLaTeXEventHandler.onExportLaTeX(this);
    }

    /**
     * Gets the term to be exported to LaTeX.
     *
     * @return The term in the form the View understands.
     */
    public HighlightedLambdaExpression getHighlightableLambdaExpression() {
        return highlightedLambdaExpression;
    }
}
