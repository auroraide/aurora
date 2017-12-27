package aurora.client.view;

import aurora.client.view.editor.Editor;
import aurora.client.view.sidebar.Sidebar;
import aurora.client.view.topmenu.TopMenu;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DesktopView extends Composite{
    interface DesktopViewUiBinder extends UiBinder<Widget, DesktopView> {
    }

    private static DesktopViewUiBinder ourUiBinder = GWT.create(DesktopViewUiBinder.class);

    @UiField
    TopMenu topMenu;

    @UiField
    Sidebar sidebar;

    @UiField
    Editor editor;

    public DesktopView() {

        initWidget(ourUiBinder.createAndBindUi(this));
    }
}