package aurora.client.view.sidebar;

import aurora.client.view.sidebar.components.library.StandardLibrary;
import aurora.client.view.sidebar.components.library.UserLibrary;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Sidebar extends Composite {
    interface SidebarUiBinder extends UiBinder<VerticalPanel, Sidebar> {}
    private static final SidebarUiBinder uiBinder = GWT.create(SidebarUiBinder.class);

    //@UiField StepSetting stepSetting;
    @UiField StandardLibrary stdlib;
    @UiField UserLibrary usrlib;

    // Evaluation Strategy
    @UiField RadioButton callByValue;
    @UiField RadioButton normalOrder;
    @UiField RadioButton callByName;
    @UiField RadioButton manualSelection;

    @UiField ToggleButton nightModeSwitch;

    public Sidebar() {
        initWidget(uiBinder.createAndBindUi(this));
    }



}
