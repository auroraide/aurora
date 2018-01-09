package aurora.client.presenter;

import aurora.client.AuroraDisplay;
import aurora.client.event.ExportLaTeXEvent;
import com.google.gwt.event.shared.EventBus;

public class AuroraPresenter {
    private final EventBus eventBus;
    private final AuroraDisplay auroraDisplay;

    public AuroraPresenter(EventBus eventBus, AuroraDisplay auroraDisplay) {
        this.eventBus = eventBus;
        this.auroraDisplay = auroraDisplay;

        bind();
    }

    private void bind() {
        // binds:
        // - ShareLatexEvent
    }

    private void onShareLatex(ExportLaTeXEvent exportLaTeXEvent) {
        // convert HLE -> LaTeX String
//        auroraDisplay.displayLatexSnippetDialog();
    }

}
