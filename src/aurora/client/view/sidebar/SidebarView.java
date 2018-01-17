package aurora.client.view.sidebar;

import aurora.client.SidebarDisplay;
import aurora.client.view.popup.AddLibraryItemDialogBox;
import aurora.client.view.popup.DeleteLibraryItemDialogBox;
import aurora.client.view.sidebar.strategy.StrategySelection;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

/**
 * Provides additional options to the user.
 * Lists available advanced options the user can choose from, such as libraries and different strategies.
 */
public class SidebarView extends Composite implements SidebarDisplay {
    interface SidebarUiBinder extends UiBinder<Widget, SidebarView> {
    }
    private static SidebarUiBinder ourUiBinder = GWT.create(SidebarUiBinder.class);

    @UiField TextBox stepNumber;
    @UiField ToggleButton nightModeSwitch;
    @UiField FlexTable standardLibraryTable;
    @UiField FlexTable userLibraryTable;
    @UiField StrategySelection strategySelection;
    @UiField ListBox languageSelection;
    @UiField ListBox shareSelection;

    private EventBus eventBus;
    private final AddLibraryItemDialogBox addLibraryItemDialogBox;
    private final DeleteLibraryItemDialogBox deleteLibraryItemDialogBox;
// shareLaTeXDB ShareLinkDB are implemented in Presenter


    /**
     * Created the Sidebar.
     * In addition the add and remove library entry buttons are generated and added to the bar.
     */
    public @UiConstructor SidebarView(EventBus eventBus) {
        this.eventBus = eventBus;
        initWidget(ourUiBinder.createAndBindUi(this));
        addLibraryItemDialogBox = new AddLibraryItemDialogBox();
        deleteLibraryItemDialogBox = new DeleteLibraryItemDialogBox();
    }

    @Override
    public void closeAddLibraryItemDialog() {

    }

    @Override
    public void addUserLibraryItem(String name, String description) {

    }

    @Override
    public void removeUserLibraryItem(String name) {

    }

    @Override
    public void addStandardLibraryItem(String name, String description) {

    }

    @Override
    public void removeStandardLibraryItem(String name) {

    }

    /**
     * Getter for languageSelection.
     *
     * @return languageSelection
     */
    public ListBox getLanguageSelection() {
        return languageSelection;
    }

    /**
     * Getter for shareSelection.
     *
     * @return shareSelection
     */
    public ListBox getShareSelection() {
        return shareSelection;
    }

    /**
     * Getter for stepNumber.
     *
     * @return stepNumber
     */
    public TextBox getStepNumber() {
        return stepNumber;
    }

    /**
     * Getter for nightModeSwitch.
     *
     * @return nightModeSwitch
     */
    public ToggleButton getNightModeSwitch() {
        return nightModeSwitch;
    }
}

