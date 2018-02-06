package aurora.client.view.sidebar;

import aurora.client.SidebarDisplay;
import aurora.client.event.AddFunctionEvent;
import aurora.client.event.StepValueChangedEvent;
import aurora.client.view.popup.AddLibraryItemDialogBox;
import aurora.client.view.popup.DeleteLibraryItemDialogBox;
import aurora.client.view.sidebar.strategy.StrategySelection;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
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
    TextBox stepNumber;
    @UiField
    ToggleButton nightModeSwitch;
    @UiField
    FlexTable standardLibraryTable;
    @UiField
    FlexTable userLibraryTable;
    @UiField
    Button addFunctionButton;
    @UiField
    StrategySelection strategySelection;
    @UiField
    ListBox languageSelection;
    @UiField
    ListBox shareSelection;
    private EventBus eventBus;
    private int prevStepNumber = 1;

    /**
     * Created the Sidebar.
     * In addition the add and remove library entry buttons are generated and added to the bar.
     */
    public SidebarView(EventBus eventBus) {
        this.eventBus = eventBus;
        initWidget(ourUiBinder.createAndBindUi(this));
        stepNumber.setText(1 + "");
        addLibraryItemDialogBox = new AddLibraryItemDialogBox();
        deleteLibraryItemDialogBox = new DeleteLibraryItemDialogBox();
        nightModeSwitch.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Window.alert("Hello World!");
            }
        });

        eventWiring();
    }

    private void eventWiring() {
        wireStepNumber();
        wireAddLibraryFunction();

    }


    private void wireStepNumber() {
        //TODO What to consider, if a number greater than Integer.MAX_VALUE or Integer.MIN_VALUE being typed.
        this.stepNumber.addKeyUpHandler(event -> {
            String input = stepNumber.getText();

            if (input.matches("[0-9]+")) {
                int stepNumber = Integer.parseInt(input);
                eventBus.fireEvent(new StepValueChangedEvent(stepNumber));
                prevStepNumber = stepNumber;

            } else {
                // Allows an input of length 1 to be deleted.
                if (input.length() != 0) {
                    stepNumber.setText("" + prevStepNumber);
                }
            }
        });
    }


    private void wireAddLibraryFunction() {
        addFunctionButton.addClickHandler(event -> addLibraryItemDialogBox.show());

        // TODO Validation for Function Name. No duplicate function name. Only alphabetical
        addLibraryItemDialogBox.getNameField().addKeyUpHandler(event -> {

        });

        // TODO Validation
        addLibraryItemDialogBox.getAddButton().addClickHandler(event -> eventBus.fireEvent(new AddFunctionEvent(
                 addLibraryItemDialogBox.getNameField().getText(),
                 addLibraryItemDialogBox.getFunctionField().getText(),
                 addLibraryItemDialogBox.getDescriptionField().getText())));
    }


    @Override
    public void closeAddLibraryItemDialog() {
        addLibraryItemDialogBox.hide();
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

