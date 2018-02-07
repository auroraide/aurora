package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Occurs when the user clicks the "share LaTeX" button. Contains the term to share.
 */
public class ExportLaTeXEvent extends GwtEvent<ExportLaTeXEventHandler> {
    public static Type<ExportLaTeXEventHandler> TYPE = new Type<>();
    private final int index;

    /**
     * Simple constructor.
     *
     * @param index Gets the index of the selected step. 0 means input, 1 is the first step.
     *              Last index might be the output, as long as it has already been computed.
     */
    public ExportLaTeXEvent(int index) {
        this.index = index;
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
     * Gets the index of the selected step. 0 means input, 1 is the first step. Last index might be the output,
     * as long as it has already been computed.
     * @return Step index.
     */
    public int getIndex() {
        return index;
    }
}
