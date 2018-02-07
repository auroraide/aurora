package aurora.client.view.sidebar;

import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.client.SidebarDisplay;
import aurora.client.view.popup.AddLibraryItemDialogBox;
import aurora.client.view.popup.DeleteLibraryItemDialogBox;
import aurora.client.view.sidebar.strategy.StrategySelection;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;


/**
 * Provides additional options to the user.
 * Lists available advanced options the user can choose from, such as libraries and different strategies.
 */
public class SidebarView extends Composite implements SidebarDisplay {
    interface SidebarUiBinder extends UiBinder<Widget, SidebarView> {
    }

    private static SidebarUiBinder ourUiBinder = GWT.create(SidebarUiBinder.class);
    private final AddLibraryItemDialogBox addLibraryItemDialogBox;
    private final DeleteLibraryItemDialogBox deleteLibraryItemDialogBox;
    @UiField
    IntegerBox stepNumber;
    @UiField
    CheckBox nightModeSwitch;
    @UiField
    FlexTable standardLibraryTable;
    @UiField
    FlexTable userLibraryTable;
    @UiField
    StrategySelection strategySelection;
    @UiField
    ListBox languageSelection;
    @UiField
    ListBox shareSelection;
    private EventBus eventBus;
    Document document;

    /**
     * Created the Sidebar.
     * In addition the add and remove library entry buttons are generated and added to the bar.
     */
    public SidebarView(EventBus eventBus) {
        this.eventBus = eventBus;
        initWidget(ourUiBinder.createAndBindUi(this));
        addLibraryItemDialogBox = new AddLibraryItemDialogBox();
        deleteLibraryItemDialogBox = new DeleteLibraryItemDialogBox();
        nightModeSwitch.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Window.alert(document.getBody().getClassName());

            }
        });
        stepNumber.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                Window.alert(String.valueOf(event.getCharCode()));
            }
        });
    }

    @Override
    public void closeAddLibraryItemDialog() {

    }

    @Override
    public void displayAddLibraryItemSyntaxError(SyntaxException error) {

    }

    @Override
    public void displayAddLibraryItemSemanticError(SemanticException error) {

    }

    @Override
    public void displayAddLibraryItemNameAlreadyTaken() {

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
    public IntegerBox getStepNumber() {
        return stepNumber;
    }

    /**
     * Getter for nightModeSwitch.
     *
     * @return nightModeSwitch
     */
    public CheckBox getNightModeSwitch() {
        return nightModeSwitch;
    }

}

