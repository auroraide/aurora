package aurora.client.view;

import aurora.client.EditorDisplay;
import aurora.client.SidebarDisplay;
import aurora.client.view.editor.EditorView;
import aurora.client.view.sidebar.SidebarView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


/**
 * Knows the layout of the component tree.
 * Receives "direct" events from actionbar and emits more semantic events onto the event bus.
 * Look into the aurora.client.event package.
 * TODO this doc is incomplete.
 */
public class AuroraView extends Composite {
    private final EventBus eventBus;
    interface DesktopViewUiBinder extends UiBinder<Widget, AuroraView> {}

    private static DesktopViewUiBinder ourUiBinder = GWT.create(DesktopViewUiBinder.class);
    @UiField
    EditorView editor;
    @UiField
    SidebarView sidebar;

    public AuroraView(EventBus eventBus) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.eventBus = eventBus;
    }

    private void bind() {
    }

    public EditorDisplay getEditor() {
        return editor;
    }

    public SidebarDisplay getSidebar() {
        return sidebar;
    }
}
