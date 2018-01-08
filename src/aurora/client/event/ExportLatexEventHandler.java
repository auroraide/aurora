package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ExportLatexEventHandler extends EventHandler {
    /**
     * Called when user wants to export a given term as LaTeX.
     * @param event Latex export event.
     */
    void onExportLatex(ExportLatexEvent event);
}
