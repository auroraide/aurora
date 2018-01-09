package aurora.client.view;

import aurora.client.AuroraDisplay;
import aurora.client.EditorDisplay;
import aurora.client.SidebarDisplay;
import aurora.client.view.editor.EditorView;
import aurora.client.view.popup.ShareDialogBox;
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
    private final ShareDialogBox latexDialogBox;
    private final ShareDialogBox shortLinkDialogBox;
    @Override
    public void displayLatexSnippetDialog(String latexCode) {

    }

    @Override
    public void displayShortLinkDialog(String shortLink) {

    }

    interface DesktopViewUiBinder extends UiBinder<Widget, AuroraView> {}

    private static DesktopViewUiBinder ourUiBinder = GWT.create(DesktopViewUiBinder.class);
    @UiField
    EditorView editor;
    @UiField
    SidebarView sidebar;

    public AuroraView(EventBus eventBus) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.eventBus = eventBus;
        latexDialogBox = new ShareDialogBox("Share LaTeX");
        shortLinkDialogBox = new ShareDialogBox("Share short link");
    }

    private void bind() {
        // on click share latex (ist in sidebar):
        //      eventBus.fireEvent(new ShareLatexenvetdings(editor.balbalbalba))

        // on click share latex in step (ist in editor):
        //      eventBus.fireEvent(new ShareLatexenvetdings(editor.step[i].balbalbalba))

        // on step number change IN SIDEBAR:
        //      editor.stepnumber = ...;
        //      eventBus.fireevent(step chagned....)

        // on step number change IN EDITOR:
        //      sidebar.stepnumber = ...;
        //      eventBus.fireevent(step chagned....)
    }

    public EditorDisplay getEditor() {
        return editor;
    }

    public SidebarDisplay getSidebar() {
        return sidebar;
    }
}
