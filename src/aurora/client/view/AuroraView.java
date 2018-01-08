package aurora.client.view;

import aurora.client.presenter.AuroraPresenter;
import aurora.client.view.editor.Editor;
import aurora.client.view.sidebar.Sidebar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

//REMOVE ONCE CODEMIRROR IS NO LONGER IN PANEL

/**
 * Knows the layout of the component tree.
 * Receives "direct" events from components and emits more semantic events onto the event bus.
 * Look into the aurora.client.event package.
 * TODO this doc is incomplete.
 */
public class AuroraView extends Composite implements AuroraPresenter.Display {
    private final EventBus eventBus;

    @Override
    public void displayError(String message) {

    }

    @Override
    public String getInput() {
        return null;
    }

    @Override
    public void setInput(String input) {

    }

    interface DesktopViewUiBinder extends UiBinder<Widget, AuroraView> {}

    private static DesktopViewUiBinder ourUiBinder = GWT.create(DesktopViewUiBinder.class);
    @UiField Editor editor;
    @UiField Sidebar sidebar;

    public AuroraView(EventBus eventBus) {
        this.eventBus = eventBus;

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    private void bind() {
        // TODO bind GWT method
    }
}
