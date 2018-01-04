package aurora.client.view.sidebar;

import aurora.client.view.sidebar.components.library.AddFunctionDialogBox;
import aurora.client.view.exceptions.ExceptionDialogBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class Sidebar extends Composite {
    interface SidebarUiBinder extends UiBinder<Widget, Sidebar> {
    }

    private static SidebarUiBinder ourUiBinder = GWT.create(SidebarUiBinder.class);
    private final AddFunctionDialogBox addFunctionDialogBox;
    private final ExceptionDialogBox exceptionDialogBox;

    @UiField TextBox stepSetting;
    @UiField Button addFunction;
    @UiField ToggleButton nightModeSwitch;

    public Sidebar() {

        initWidget(ourUiBinder.createAndBindUi(this));
        addFunctionDialogBox = new AddFunctionDialogBox();
        exceptionDialogBox = new ExceptionDialogBox();
    }

    @UiHandler("addFunction")
    void onAddFunctionClicked(ClickEvent event) {
        addFunctionDialogBox.show();
        exceptionDialogBox.show();
    }
}
