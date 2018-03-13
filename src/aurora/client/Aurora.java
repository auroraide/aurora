package aurora.client;

import aurora.backend.library.HashLibrary;
import aurora.backend.library.Library;
import aurora.backend.library.MultiLibrary;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.simplifier.ChurchNumberSimplifier;
import aurora.backend.simplifier.LibraryTermSimplifier;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.Term;
import aurora.client.presenter.AuroraPresenter;
import aurora.client.presenter.EditorPresenter;
import aurora.client.presenter.SidebarPresenter;
import aurora.client.view.AuroraView;
import aurora.client.view.editor.EditorView;
import aurora.client.view.sidebar.SidebarView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.debug.client.DebugInfo;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import java.util.ArrayList;

/**
 * Responsible for intitalising the Aurora Web Application.
 * <p>
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Aurora implements EntryPoint {

    /**
     * This is the entry point method. Sets up and initialises the Aurora Web Application.
     */
    public void onModuleLoad() {
        // Remove the need to specify a debugId prefix.
        DebugInfo.setDebugIdPrefix("");

        EventBus eventBus = GWT.create(SimpleEventBus.class);

        // views/displays, ordering is important
        SidebarView sidebarView = new SidebarView(eventBus);
        EditorView editorView = new EditorView(eventBus);
        AuroraView auroraView = new AuroraView(eventBus, sidebarView, editorView);

        // libraries
        Library userLib = new HashLibrary();
        Library stdLib = new HashLibrary();
        ArrayList<Term> steps = new ArrayList<>();

        Library library = new MultiLibrary(userLib, stdLib);

        LambdaLexer lexer = new LambdaLexer();
        LambdaParser parser = new LambdaParser(library);

        // presenters
        AuroraPresenter auroraPresenter = new AuroraPresenter(
                eventBus,
                auroraView,
                steps);

        SidebarPresenter sidebarPresenter = new SidebarPresenter(
                eventBus,
                auroraView.getSidebar(),
                stdLib,
                userLib,
                lexer,
                parser);

        EditorPresenter editorPresenter = new EditorPresenter(
                eventBus,
                auroraView.getEditor(),
                stdLib,
                userLib,
                new ChurchNumberSimplifier(),
                new LibraryTermSimplifier(library),
                steps,
                lexer,
                parser);

        RootLayoutPanel.get().add(auroraView);
    }
}
