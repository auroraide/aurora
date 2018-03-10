package aurora.client.event;

import aurora.backend.HighlightedLambdaExpression;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Occurs when the user clicks the "share LaTeX" button. Contains the term to share.
 */
public class ExportLaTeXEvent extends GwtEvent<ExportLaTeXEventHandler> {
    public static Type<ExportLaTeXEventHandler> TYPE = new Type<>();
    /**
     * Represents the index of the result step.
     */
    public static final int RESULT = -1;

    /**
     * Represents the index of input.
     */
    public static final int INPUT = 0;
    private final int index;

    /**
     * Simple constructor.
     *
     * @param index Gets the index of the selected step. 0 means input, 1 is the first step and -1 means result.
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
     * Gets the index of the selected step. 0 means input, 1 is the first step and -1 means result.
     * @return Step index.
     */
    public int getIndex() {
        return index;
    }
}
