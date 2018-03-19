package aurora.client.presenter;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.ShareLaTeX;
import aurora.backend.encoders.GistEncoder;
import aurora.backend.library.Library;
import aurora.backend.tree.Term;
import aurora.client.AuroraDisplay;
import aurora.client.EditorDisplay;
import aurora.client.event.ExportLaTeXAllEvent;
import aurora.client.event.ExportLaTeXEvent;
import aurora.client.event.ShareEmailAllEvent;
import aurora.client.event.ShareEmailEvent;
import aurora.client.event.ShareLinkAllEvent;
import aurora.client.event.ShareLinkEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * <code>AuroraPresenter</code> is responsible for the presentation logic.
 * <p>
 * It fetches user events and acts upon those by using the backend, which presents the model.
 * <code>Aurora Presenter</code> then updates the view through via the {@link AuroraDisplay}.
 */
public class AuroraPresenter {
    private final EventBus eventBus;
    private final AuroraDisplay auroraDisplay;
    private final EditorDisplay editorDisplay;
    private final ArrayList<Step> steps;
    private final Library stdLib;

    /**
     * Creates an <code>AuroraPresenter</code> with an event bus and a {@link AuroraDisplay}.
     * @param eventBus      The event bus.
     * @param auroraDisplay The aurora display.
     * @param editorDisplay Editor presenter. Used to get input.
     * @param stdLib Standard library.
     * @param steps Shared state between presenters. Index 0 is input.
     */
    public AuroraPresenter(EventBus eventBus, AuroraDisplay auroraDisplay, EditorDisplay editorDisplay, Library stdLib, ArrayList<Step> steps) {
        this.eventBus = eventBus;
        this.auroraDisplay = auroraDisplay;
        this.editorDisplay = editorDisplay;
        this.stdLib = stdLib;
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
        GistEncoder ge = new GistEncoder(stdLib);
        ge.encode(editorDisplay.getInput(), stdLib, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                auroraDisplay.displayShortLinkDialog("ERROR");
            }

            @Override
            public void onSuccess(String result) {
                auroraDisplay.displayShortLinkDialog(result);
            }
        });
    }

    private void onShareLink(ShareLinkEvent e) {
        Step step = steps.get(e.getStep());
        GistEncoder ge = new GistEncoder(stdLib);
        ge.encode(step.getHle().toString(), stdLib, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                auroraDisplay.displayShortLinkDialog("ERROR");
            }

            @Override
            public void onSuccess(String result) {
                auroraDisplay.displayShortLinkDialog(result);
            }
        });
    }

    private void onShareEmailAll(ShareEmailAllEvent e) {
    }

    private void onShareEmail(ShareEmailEvent e) {
    }

    private void onExportLaTeXAll(ExportLaTeXAllEvent e) {
        StringBuilder sb = new StringBuilder();
        for (Term t : steps.stream().map(Step::getTerm).collect(Collectors.toList())) {
            String latex = new ShareLaTeX(new HighlightableLambdaExpression(t)).generateLaTeX();
            sb.append(latex);
            sb.append("\n");
        }
        auroraDisplay.displayLatexSnippetDialog(sb.toString());
    }

    private void onExportLaTeX(ExportLaTeXEvent e) {
        HighlightedLambdaExpression hle;
        if (e.getIndex() != ExportLaTeXEvent.RESULT) {
            hle = new HighlightableLambdaExpression(steps.get(e.getIndex()).getTerm());
        } else {
            GWT.log(String.valueOf(steps.size()));
            hle = new HighlightableLambdaExpression(steps.get(steps.size() - 1).getTerm());
        }
        ShareLaTeX shareLaTeX = new ShareLaTeX(hle);
        auroraDisplay.displayLatexSnippetDialog(shareLaTeX.generateLaTeX());
    }
}
