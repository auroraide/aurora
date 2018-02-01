package aurora.client;

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
import com.google.gwt.user.client.Timer;

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

        // presenters
        AuroraPresenter auroraPresenter = new AuroraPresenter(eventBus, auroraView);
        SidebarPresenter sidebarPresenter = new SidebarPresenter(eventBus, auroraView.getSidebar());
        EditorPresenter editorPresenter = new EditorPresenter(eventBus, auroraView.getEditor());

        RootLayoutPanel.get().add(auroraView);

        reloadCodeMirror(editorView);
    }

    /**
     * Temporary way to get CodeMirror up and running.
     * Will be replaced with a scheduler in the near future.
     * Maybe.
     * Just pretend it's not there...
     */
    private void reloadCodeMirror(EditorView editorView) {
        Timer timer = new Timer() {
            @Override
            public void run() {
                editorView.reloadCM();
            }
        };
        timer.schedule(0);
    }

    private native void console(String text) /*-{
        console.log(text);
    }-*/;
}
