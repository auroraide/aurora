package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles an {@link ExportLaTeXAllEvent}.
 */
public interface ExportLaTeXAllEventHandler extends EventHandler {
    /**
     * Called when user wants to export all lambda terms from input, steps and result.
     *
     * @param exportLaTeXAllEvent The event.
     */
    void onExportLatexAll(ExportLaTeXAllEvent exportLaTeXAllEvent);
}
