package aurora.client.view;

import aurora.backend.HighlightedLambdaExpression;
import aurora.client.AuroraDisplay;
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
public class AuroraView extends Composite implements AuroraDisplay {
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

    @Override
    public void displayWarning(String message) {

    }

    /**
     * @param stepNumber The step number to be set in the view
     */
    @Override
    public void setStepNumber(int stepNumber) {

    }

    public EditorView getEditor() {
        return editor;
    }

    public SidebarView getSidebar() {
        return sidebar;
    }
}
