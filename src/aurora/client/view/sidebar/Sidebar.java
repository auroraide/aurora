package aurora.client.view.sidebar;

import aurora.client.view.popup.AddFunctionDialogBox;
import aurora.client.view.popup.DeleteLibraryItemDialogBox;
import aurora.client.view.sidebar.components.strategy.StrategySelection;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

public class Sidebar extends Composite {
    interface SidebarUiBinder extends UiBinder<Widget, Sidebar> {
    }

    private static SidebarUiBinder ourUiBinder = GWT.create(SidebarUiBinder.class);
    @UiField TextBox stepNumber;
    @UiField ToggleButton nightModeSwitch;
    @UiField FlexTable standardLibraryTable;
    @UiField FlexTable userLibraryTable;
    @UiField StrategySelection strategySelection;
    @UiField ListBox languageSelection;
    @UiField ListBox shareSelection;
    private final AddFunctionDialogBox addFunctionDialogBox;
    private final DeleteLibraryItemDialogBox deleteLibraryItemDialogBox;
// shareLatexDB ShareLinkDB are implemented in Presenter



    public Sidebar() {
        initWidget(ourUiBinder.createAndBindUi(this));
        addFunctionDialogBox = new AddFunctionDialogBox();
        deleteLibraryItemDialogBox = new DeleteLibraryItemDialogBox();
    }



    public ListBox getLanguageSelection() {
        return languageSelection;
    }

    public ListBox getShareSelection() {
        return shareSelection;
    }

    public TextBox getStepNumber() {
        return stepNumber;
    }

    public ToggleButton getNightModeSwitch() {
        return nightModeSwitch;
    }

    public void setStandardLibraryTable(FlexTable standardLibraryTable) {
        this.standardLibraryTable = standardLibraryTable;
    }

    public void setUserLibraryTable(FlexTable userLibraryTable) {
        this.userLibraryTable = userLibraryTable;
    }

    public void setStrategySelection(StrategySelection strategySelection) {
        this.strategySelection = strategySelection;
    }
}