package aurora.client.view.sidebar;

import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.client.SidebarDisplay;
import aurora.client.event.AddFunctionEvent;
import aurora.client.event.StepValueChangedEvent;
import aurora.client.view.popup.AddLibraryItemDialogBox;
import aurora.client.view.popup.DeleteLibraryItemDialogBox;
import aurora.client.view.sidebar.strategy.StrategySelection;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.TextBox;
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
    CheckBox nightModeSwitch;
    @UiField
    FlexTable standardLibraryTable;
    @UiField
    FlexTable userLibraryTable;
    @UiField
    Button addFunctionButton;
    @UiField
    StrategySelection strategySelection;
    @UiField
    FlowPanel shareAndLanguage;
    private EventBus eventBus;
    private int prevStepNumber = 1;
    Document document;

    /**
     * Created the Sidebar.
     * In addition the add and remove library entry buttons are generated and added to the bar.
     */
    public SidebarView(EventBus eventBus) {
        this.eventBus = eventBus;
        initWidget(ourUiBinder.createAndBindUi(this));
        this.stepNumber.setText(1 + "");
        this.addLibraryItemDialogBox = new AddLibraryItemDialogBox();
        this.deleteLibraryItemDialogBox = new DeleteLibraryItemDialogBox();
        setupShareLanguageMenu();

        eventWiring();
    }

    private void setupShareLanguageMenu() {
        MenuBar languageMenu = new MenuBar(true);
        languageMenu.setAnimationEnabled(false);
        languageMenu.addStyleName("languageButton");
        languageMenu.addItem("language", setupLanguageMenuBar());
        this.shareAndLanguage.add(languageMenu);

        MenuBar shareMenu = new MenuBar(true);
        languageMenu.setAnimationEnabled(false);
        languageMenu.addStyleName("shareButton");
        languageMenu.addItem(" ", setupShareMenuBar());
        this.shareAndLanguage.add(shareMenu);
    }

    private MenuBar setupShareMenuBar() {
        MenuBar shareMenuBar = new MenuBar(true);
        shareMenuBar.addStyleName("shareMenuBar");
        shareMenuBar.addItem("LaTeX", new Command() {
            @Override
            public void execute() {
                Window.alert("hhh");
            }
        });
        shareMenuBar.addItem("Link", new Command() {
            @Override
            public void execute() {
                Window.alert("hhh");
            }
        });
        return shareMenuBar;
    }

    private MenuBar setupLanguageMenuBar() {
        MenuBar languageMenuBar = new MenuBar(true);
        languageMenuBar.addStyleName("languageMenuBar");
        languageMenuBar.addItem("ENG", new Command() {
            @Override
            public void execute() {
                Window.alert("hhh");
            }
        });
        languageMenuBar.addItem("RU", new Command() {
            @Override
            public void execute() {
                Window.alert("hhh");
            }
        });
        languageMenuBar.addItem("DE", new Command() {
            @Override
            public void execute() {
                Window.alert("hhh");
            }
        });
        return languageMenuBar;
    }

    private void eventWiring() {
        wireStepNumber();
        wireAddLibraryFunction();

        
    }
    
    
    private void wireStepNumber() {
        this.stepNumber.addKeyUpHandler(event -> {
            String input = stepNumber.getText();
            boolean successful = true;

            if (input.matches("[0-9]+")) {
                int step;

                try {
                    step = Integer.parseInt(input);

                    // Can only be a positive number in range of [1,2048].
                    if (step < 1) {
                        step = 1;
                        SidebarView.this.stepNumber.setText("" + step);
                    } else if (step >= 2048) {
                        step = 2048;
                        SidebarView.this.stepNumber.setText("" + step);
                    }

                } catch (NumberFormatException nfe) {
                    // Thrown when number is bigger than Integer.MAX_VALUE.
                    // Setting step to allowed maximum of 2048, if this is the case.
                    step = 2048;
                    SidebarView.this.stepNumber.setText("" + step);
                }

                SidebarView.this.eventBus.fireEvent(new StepValueChangedEvent(step));
                SidebarView.this.prevStepNumber = step;
                
            } else {
                // Allows an input of length 1 to be deleted.
                if (input.length() != 0) {
                    SidebarView.this.stepNumber.setText("" + prevStepNumber);
                }
            }
        });
    }


    private void wireAddLibraryFunction() {
        this.addFunctionButton.addClickHandler(event -> SidebarView.this.addLibraryItemDialogBox.show());
        this.addLibraryItemDialogBox.getNameField().addKeyUpHandler(event -> {
        });

        // SidebarPresenter does validation.
        this.addLibraryItemDialogBox.getAddButton().addClickHandler(event -> SidebarView.this.eventBus.fireEvent(
                new AddFunctionEvent(
                SidebarView.this.addLibraryItemDialogBox.getNameField().getText(),
                SidebarView.this.addLibraryItemDialogBox.getFunctionField().getText(),
                SidebarView.this.addLibraryItemDialogBox.getDescriptionField().getText())));
    }


    @Override
    public void closeAddLibraryItemDialog() {
        this.addLibraryItemDialogBox.clearAddLibraryItemDialogBox();
        this.addLibraryItemDialogBox.hide();
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
    public void displayAddLibraryItemInvalidName() {

    }

    @Override
    public void addUserLibraryItem(String name, String description) {
    }

    @Override
    public void addStandardLibraryItem(String name, String description) {

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
    public CheckBox getNightModeSwitch() {
        return nightModeSwitch;
    }


}

