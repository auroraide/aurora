package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles {@link ExportLaTeXEvent}.
 */
public interface ExportLaTeXEventHandler extends EventHandler {
    /**
     * Called when user wants to export a given term as LaTeX.
     *
     * @param event LaTeX export event.
     */
    void onExportLaTeX(ExportLaTeXEvent event);
}
