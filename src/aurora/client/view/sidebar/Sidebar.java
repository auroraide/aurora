package aurora.client.view.sidebar;

import aurora.client.view.sidebar.components.library.StandardLibrary;
import aurora.client.view.sidebar.components.library.UserLibrary;
import aurora.client.view.sidebar.components.strategy.EvaluationStrategy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

public class Sidebar extends Composite {
    interface SidebarUiBinder extends UiBinder<Widget, Sidebar> {}
    private static final SidebarUiBinder uiBinder = GWT.create(SidebarUiBinder.class);

    //@UiField StepSetting stepSetting;
    @UiField StandardLibrary stdlib;
    @UiField UserLibrary usrlib;
    @UiField Button addFunction;

    // Evaluation Strategy
    @UiField EvaluationStrategy strategy;

    @UiField ToggleButton nightModeSwitch;

    public Sidebar() {

        initWidget(uiBinder.createAndBindUi(this));
    }



}
