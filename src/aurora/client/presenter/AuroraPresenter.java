package aurora.client.presenter;

import aurora.client.AuroraDisplay;
import aurora.client.event.ExportLaTeXEvent;
import com.google.gwt.event.shared.EventBus;

/**
 * <p>
 *     <code>AuroraPresenter</code> is responsible for the presentation logic.
 * </p>
 *
 * <p>
 *     It fetches user events and acts upon those by using the backend, which presents the model.
 *     <code>Aurora Presenter</code> then updates the view through via the {@link AuroraDisplay}.
 * </p>
 */
public class AuroraPresenter {
    private final EventBus eventBus;
    private final AuroraDisplay auroraDisplay;

    /**
     * Creates an <code>AuroraPresenter</code> with an event bus and a {@link AuroraDisplay}.
     *
     * @param eventBus The event bus.
     * @param auroraDisplay The aurora display.
     */
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
