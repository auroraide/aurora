package aurora.client;

import aurora.backend.library.Library;
import aurora.client.presenter.AuroraPresenter;
import aurora.client.presenter.EditorPresenter;
import aurora.client.presenter.SidebarPresenter;
import aurora.client.view.AuroraView;
import aurora.client.view.editor.EditorView;
import aurora.client.view.sidebar.SidebarView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.RootLayoutPanel;

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
        EventBus eventBus = GWT.create(SimpleEventBus.class);

        // views/displays, ordering is important
        SidebarView sidebarView = new SidebarView(eventBus);
        EditorView editorView = new EditorView(eventBus);
        AuroraView auroraView = new AuroraView(eventBus, sidebarView, editorView);

        // libraries
        Library userLib = new Library();
        Library stdLib = new Library();

        // presenters
        AuroraPresenter auroraPresenter = new AuroraPresenter(eventBus, auroraView);
        SidebarPresenter sidebarPresenter = new SidebarPresenter(eventBus, auroraView.getSidebar(), stdLib, userLib);
        EditorPresenter editorPresenter = new EditorPresenter(eventBus, auroraView.getEditor(), stdLib, userLib);

        RootLayoutPanel.get().add(auroraView);
    }
}
