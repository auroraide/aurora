package aurora.client.view.sidebar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import aurora.client.view.sidebar.components.library.StandardLibraryTable;
import aurora.client.view.sidebar.components.library.UserLibraryTable;
import aurora.client.view.sidebar.components.language.LanguageSelection;
import aurora.client.view.sidebar.components.share.ShareSelection;

public class Sidebar extends Composite {
    interface SidebarUiBinder extends UiBinder<Widget, Sidebar> {
    }

    private static SidebarUiBinder ourUiBinder = GWT.create(SidebarUiBinder.class);
    @UiField private TextBox stepNumber;
    @UiField private ToggleButton nightModeSwitch;
    private StandardLibraryTable standardLibraryTable;
    private UserLibraryTable userLibraryTable;
    private StrategySelection strategySelection;
    private LanguageSelection languageSelection;
    private ShareSelection shareSelection;

    public Sidebar() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

}