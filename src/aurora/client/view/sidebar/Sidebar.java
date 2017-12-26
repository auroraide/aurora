package aurora.client.view.sidebar;

import aurora.client.view.sidebar.components.library.StandardLibrary;
import aurora.client.view.sidebar.components.library.UserLibrary;
import aurora.client.view.sidebar.components.strategy.EvaluationStrategy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Sidebar extends Composite {
    interface SidebarUiBinder extends UiBinder<VerticalPanel, Sidebar> {}
    private static final SidebarUiBinder uiBinder = GWT.create(SidebarUiBinder.class);

    //@UiField StepSetting stepSetting;
    @UiField StandardLibrary stdlib;
    @UiField UserLibrary usrlib;

    // Evaluation Strategy
    @UiField EvaluationStrategy strategy;

    @UiField ToggleButton nightModeSwitch;

    public Sidebar() {

        initWidget(uiBinder.createAndBindUi(this));
    }



}
