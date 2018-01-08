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
    @UiField TextBox stepNumber;
    @UiField ToggleButton nightModeSwitch;
    private StandardLibraryTable standardLibraryTable;
    private UserLibraryTable userLibraryTable;
    private StrategySelection strategySelection;
    @UiField ListBox languageSelection;
    @UiField ListBox shareSelection;
    private String auroraActiveLanguage;

    public Sidebar() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    public String getLanguageSelection() {
        return shareSelection.getSelectedValue;
    }

    public String getShareSelection() {
        return languageSelection.getSelectedValue;
    }
    public void updateLanguages(String[] languagesAvailable) {

    }
    public void setActiveLanguage(String activeLanguage) {
        auroraActiveLanguage = activeLanguage;
    }

}