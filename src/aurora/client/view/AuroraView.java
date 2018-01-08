package aurora.client.view;

import aurora.backend.HighlightedLambdaExpression;
import aurora.client.Display;
import aurora.client.view.editor.Editor;
import aurora.client.view.sidebar.Sidebar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


/**
 * Knows the layout of the component tree.
 * Receives "direct" events from components and emits more semantic events onto the event bus.
 * Look into the aurora.client.event package.
 * TODO this doc is incomplete.
 */
public class AuroraView extends Composite implements Display {
    private final EventBus eventBus;
    interface DesktopViewUiBinder extends UiBinder<Widget, AuroraView> {}

    private static DesktopViewUiBinder ourUiBinder = GWT.create(DesktopViewUiBinder.class);
    @UiField
    Editor editor;
    @UiField
    Sidebar sidebar;

    public AuroraView(EventBus eventBus) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.eventBus = eventBus;
    }

    private void bind() {
        // TODO bind GWT method
    }


    @Override
    public void displaySyntaxError(String message) {

    }

    @Override
    public void displayWarning(String message) {

    }

    @Override
    public String getInput() {
        return null;
    }

    @Override
    public void closeAddFunctionDiaglog() {

    }

    @Override
    public void addFunction(String name, String description) {

    }

    @Override
    public void removeFunction(String name) {

    }

    @Override
    public void setInput(HighlightedLambdaExpression highlightedLambdaExpression) {

    }
}
