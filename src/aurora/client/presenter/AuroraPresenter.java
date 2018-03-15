package aurora.client.presenter;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.ShareLaTeX;
import aurora.backend.tree.Term;
import aurora.client.AuroraDisplay;
import aurora.client.event.ExportLaTeXAllEvent;
import aurora.client.event.ExportLaTeXEvent;
import aurora.client.event.ShareEmailAllEvent;
import aurora.client.event.ShareEmailEvent;
import aurora.client.event.ShareLinkAllEvent;
import aurora.client.event.ShareLinkEvent;
import com.google.gwt.event.shared.EventBus;
import java.util.ArrayList;

/**
 * <code>AuroraPresenter</code> is responsible for the presentation logic.
 * <p>
 * It fetches user events and acts upon those by using the backend, which presents the model.
 * <code>Aurora Presenter</code> then updates the view through via the {@link AuroraDisplay}.
 */
public class AuroraPresenter {
    private final EventBus eventBus;
    private final AuroraDisplay auroraDisplay;
    private final ArrayList<Term> steps;

    /**
     * Creates an <code>AuroraPresenter</code> with an event bus and a {@link AuroraDisplay}.
     *  @param eventBus      The event bus.
     * @param auroraDisplay The aurora display.
     * @param steps Shared state between presenters. Index 0 is input.
     */
    public AuroraPresenter(EventBus eventBus, AuroraDisplay auroraDisplay, ArrayList<Term> steps) {
        this.eventBus = eventBus;
        this.auroraDisplay = auroraDisplay;
        this.steps = steps;

        bind();
    }

    private void bind() {
        eventBus.addHandler(ExportLaTeXEvent.TYPE, this::onExportLaTeX);
        eventBus.addHandler(ExportLaTeXAllEvent.TYPE, this::onExportLaTeXAll);
        eventBus.addHandler(ShareEmailEvent.TYPE, this::onShareEmail);
        eventBus.addHandler(ShareEmailAllEvent.TYPE, this::onShareEmailAll);
        eventBus.addHandler(ShareLinkEvent.TYPE, this::onShareLink);
        eventBus.addHandler(ShareLinkAllEvent.TYPE, this::onShareLinkAll);
    }

    private void onShareLinkAll(ShareLinkAllEvent e) {
    }

    private void onShareLink(ShareLinkEvent e) {
        HighlightedLambdaExpression hle = new HighlightableLambdaExpression(steps.get(e.getStep()));
        auroraDisplay.displayShortLinkDialog();
    }

    private void onShareEmailAll(ShareEmailAllEvent e) {
    }

    private void onShareEmail(ShareEmailEvent e) {
    }

    private void onExportLaTeXAll(ExportLaTeXAllEvent e) {
        StringBuilder sb = new StringBuilder();
        for (Term t : steps) {
            String latex = new ShareLaTeX(new HighlightableLambdaExpression(t)).generateLaTeX();
            sb.append(latex);
            sb.append("\n");
        }
        auroraDisplay.displayLatexSnippetDialog(sb.toString());
    }

    private void onExportLaTeX(ExportLaTeXEvent e) {
        HighlightedLambdaExpression hle = new HighlightableLambdaExpression(steps.get(e.getIndex()));
        ShareLaTeX shareLaTeX = new ShareLaTeX(hle);
        auroraDisplay.displayLatexSnippetDialog(shareLaTeX.generateLaTeX());
    }
}
