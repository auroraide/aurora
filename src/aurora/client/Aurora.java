package aurora.client;

import aurora.client.presenter.EditorPresenter;
import aurora.client.presenter.SidebarPresenter;
import aurora.client.view.AuroraView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Aurora implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        EventBus eventBus = GWT.create(SimpleEventBus.class);

        // views/displays
        AuroraView view = new AuroraView(eventBus);

        // presenters
        SidebarPresenter sidebarPresenter = new SidebarPresenter(eventBus, view.getSidebar());
        EditorPresenter editorPresenter = new EditorPresenter(eventBus, view.getEditor());

        RootLayoutPanel.get().add(view);
    }
}
