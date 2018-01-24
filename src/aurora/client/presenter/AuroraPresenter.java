package aurora.client.presenter;

import aurora.client.AuroraDisplay;
import aurora.client.event.ExportLaTeXEvent;
import com.google.gwt.event.shared.EventBus;

/**
 * <code>AuroraPresenter</code> is responsible for the presentation logic.
 * <p>
 * It fetches user events and acts upon those by using the backend, which presents the model.
 * <code>Aurora Presenter</code> then updates the view through via the {@link AuroraDisplay}.
 */
public class AuroraPresenter {
    private final EventBus eventBus;
    private final AuroraDisplay auroraDisplay;
    private final EditorPresenter editorPresenter;
    private final SidebarPresenter sidebarPresenter;
    /**
     * Creates an <code>AuroraPresenter</code> with an event bus and a {@link AuroraDisplay}.
     *
     * @param eventBus      The event bus.
     * @param auroraDisplay The aurora display.
     */
    public AuroraPresenter(EventBus eventBus, AuroraDisplay auroraDisplay, EditorPresenter editorPresenter,
                           SidebarPresenter sidebarPresenter) {
        this.eventBus = eventBus;
        this.auroraDisplay = auroraDisplay;
        this.editorPresenter = editorPresenter;
        this.sidebarPresenter = sidebarPresenter;

        bind();
    }

    private void bind() {
        // binds:
        // - ShareLatexEvent
    }

    private void onShareLatex(ExportLaTeXEvent exportLaTeXEvent) {
        // convert HLE -> LaTeX String
        // auroraDisplay.displayLatexSnippetDialog();
    }

}
