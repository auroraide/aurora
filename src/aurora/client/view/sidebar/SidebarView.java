package aurora.client.view.sidebar;

import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.client.SidebarDisplay;
import aurora.client.event.AddFunctionEvent;
import aurora.client.event.DeleteFunctionEvent;
import aurora.client.event.EvaluationStrategyChangedEvent;
import aurora.client.event.ExportLaTeXAllEvent;
import aurora.client.event.ShareLinkAllEvent;
import aurora.client.event.StepValueChangedEvent;
import aurora.client.event.ViewStateChangedEvent;
import aurora.client.view.popup.AddLibraryItemDialogBox;
import aurora.client.view.popup.InfoDialogBox;
import aurora.client.view.sidebar.strategy.StrategySelection;
import aurora.client.view.sidebar.strategy.StrategyType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;


/**
 * Provides additional options to the user.
 * Lists available advanced options the user can choose from, such as libraries and different strategies.
 */
public class SidebarView extends Composite implements SidebarDisplay {
    interface SidebarUiBinder extends UiBinder<Widget, SidebarView> {
    }

    private static SidebarUiBinder ourUiBinder = GWT.create(SidebarUiBinder.class);

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
    @UiField
    StackLayoutPanel stackLibraries;

    final AddLibraryItemDialogBox addLibraryItemDialogBox;
    final MenuBar shareAndLanguageMenu;
    final MenuBar languageMenu;
    final MenuBar shareMenu;

    private final InfoDialogBox errorMessageDialogBox;
    private EventBus eventBus;
    private int prevStepNumber = 1;
    private ArrayList<String> userlib;

    private LinkElement linkDarkModeAuroraStyle;
    private LinkElement linkLightModeAuroraStyle;

    private LinkElement linkDarkModeSidebarStyle;
    private LinkElement linkLightModeSidebarStyle;

    private LinkElement linkDarkModeEditorStyle;
    private LinkElement linkLightModeEditorStyle;

    private LinkElement linkLightCMStyle;
    private LinkElement linkDarkCMStyle;

    private LinkElement editorStyle;
    private LinkElement sidebarStyle;
    private LinkElement auroraStyle;
    private LinkElement darkModeStyle;
    private LinkElement lightModeStyle;


    /**
     * Created the Sidebar.
     * In addition the add and remove library entry buttons are generated and added to the bar.
     */
    public SidebarView(EventBus eventBus) {
        this.eventBus = eventBus;
        initWidget(ourUiBinder.createAndBindUi(this));

        this.addLibraryItemDialogBox = new AddLibraryItemDialogBox();
        this.errorMessageDialogBox = new InfoDialogBox();
        this.languageMenu = new MenuBar(true);
        this.shareMenu = new MenuBar(true);
        this.shareAndLanguageMenu = new MenuBar();
        shareAndLanguageMenu.setAutoOpen(false);
        shareAndLanguageMenu.setAnimationEnabled(true);
        this.userlib = new ArrayList<>();

        this.stepNumber.setText(1 + "");
        //this.stackLibraries.showWidget(0);
        this.nightModeSwitch.setValue(true);

        setupShareLanguageMenu();
        createCssLinks();
        setupDefaultAuroraCSS();
        eventWiring();
        shareAndLanguageMenu.addItem(new MenuItem("language", languageMenu));
        shareAndLanguageMenu.addItem(new MenuItem("", shareMenu));
        shareAndLanguage.add(shareAndLanguageMenu);
        shareAndLanguageMenu.setStyleName("shareAndLanguage");
    }

    @Override
    public void closeAddLibraryItemDialog() {
        this.addLibraryItemDialogBox.clearAddLibraryItemDialogBox();
        this.addLibraryItemDialogBox.hide();
    }

    @Override
    public void displayAddLibraryItemSyntaxError(SyntaxException error) {
        this.errorMessageDialogBox.setDescription(error.getMessage());
        this.errorMessageDialogBox.show();
    }

    @Override
    public void displayAddLibraryItemSemanticError(SemanticException error) {
        this.errorMessageDialogBox.setDescription((error.getMessage()));
        this.errorMessageDialogBox.show();
    }

    @Override
    public void displayAddLibraryItemNameAlreadyTaken() {
        this.errorMessageDialogBox.setDescription("The function name is already taken. Please choose another one.");
        this.errorMessageDialogBox.show();
    }

    @Override
    public void displayAddLibraryItemInvalidName() {
        this.errorMessageDialogBox.setDescription("The function name is not valid.");
        this.errorMessageDialogBox.show();
    }

    @Override
    public void addUserLibraryItem(String name, String description) {
        int row = userLibraryTable.getRowCount();
        this.userlib.add(name);
        this.userLibraryTable.setText(row, 0, "$" + name);
        this.userLibraryTable.setText(row, 1, description);

        Button removeLibraryItemButton = new Button("x");
        removeLibraryItemButton.setStyleName("deleteUserLibraryItem");
        removeLibraryItemButton.ensureDebugId("removeLibraryItemButton-" + row);
        removeLibraryItemButton.addClickHandler(event -> {
            Scheduler scheduler = Scheduler.get();
            scheduler.scheduleDeferred((Command) () -> {
                SidebarView.this.eventBus.fireEvent(new DeleteFunctionEvent(name));
            });
            int removedIndex = userlib.indexOf(name);
            SidebarView.this.userlib.remove(removedIndex);
            SidebarView.this.userLibraryTable.removeRow(removedIndex);
        });
        userLibraryTable.setWidget(row, 2, removeLibraryItemButton);
    }

    @Override
    public void addStandardLibraryItem(String name, String description) {
        int row = this.standardLibraryTable.getRowCount();
        this.standardLibraryTable.setText(row, 0, "$" + name);
        this.standardLibraryTable.setText(row, 1, description);
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

    private void setupShareLanguageMenu() {
        // sets up language menu
        languageMenu.setAnimationEnabled(false);
        languageMenu.addItem("RU", (Command) () -> Window.Location.assign("https://aurora.younishd.fr/?locale=ru"));
        languageMenu.addItem("ENG", (Command) () -> Window.Location.assign("https://aurora.younishd.fr/"));
        languageMenu.setStyleName("languageMenu");
        languageMenu.setSize("250px", "auto");

        // sets up share menu
        shareMenu.setAnimationEnabled(false);
        shareMenu.addItem("LaTeX", (Command) () -> SidebarView.this.eventBus.fireEvent(new ExportLaTeXAllEvent()));
        shareMenu.addItem("Link", (Command) () -> SidebarView.this.eventBus.fireEvent(new ShareLinkAllEvent()));
        shareMenu.setStyleName("shareMenu");
        shareMenu.setSize("250px", "auto");
    }

    private void createCssLinks() {
        /*linkDarkModeAuroraStyle = Document.get().createLinkElement();
        linkDarkModeAuroraStyle.setHref("css/AuroraDark.css");
        linkDarkModeAuroraStyle.setRel("stylesheet");
        linkLightModeAuroraStyle = Document.get().createLinkElement();
        linkLightModeAuroraStyle.setHref("css/AuroraLight.css");
        linkLightModeAuroraStyle.setRel("stylesheet");

        linkDarkModeSidebarStyle = Document.get().createLinkElement();
        linkDarkModeSidebarStyle.setHref("css/SidebarDark.css");
        linkDarkModeSidebarStyle.setRel("stylesheet");
        linkLightModeSidebarStyle = Document.get().createLinkElement();
        linkLightModeSidebarStyle.setHref("css/SidebarLight.css");
        linkLightModeSidebarStyle.setRel("stylesheet");

        linkDarkModeEditorStyle = Document.get().createLinkElement();
        linkDarkModeEditorStyle.setHref("css/EditorDark.css");
        linkDarkModeEditorStyle.setRel("stylesheet");
        linkLightModeEditorStyle = Document.get().createLinkElement();
        linkLightModeEditorStyle.setHref("css/EditorLight.css");
        linkLightModeEditorStyle.setRel("stylesheet");
*/
        linkDarkCMStyle = Document.get().createLinkElement();
        linkDarkCMStyle.setHref("css/CodeMirror/material.css");
        linkDarkCMStyle.setRel("stylesheet");

        editorStyle = Document.get().createLinkElement();
        editorStyle.setHref("css/Editor.css");
        editorStyle.setRel("stylesheet");
        sidebarStyle = Document.get().createLinkElement();
        sidebarStyle.setHref("css/Sidebar.css");
        sidebarStyle.setRel("stylesheet");
        auroraStyle = Document.get().createLinkElement();
        auroraStyle.setHref("css/Aurora.css");
        auroraStyle.setRel("stylesheet");


        darkModeStyle = Document.get().createLinkElement();
        darkModeStyle.setHref("css/AuroraDarkMode.css");
        darkModeStyle.setRel("stylesheet");
        lightModeStyle = Document.get().createLinkElement();
        lightModeStyle.setHref("css/AuroraStrawberryCheescakeMode.css");
        lightModeStyle.setRel("stylesheet");
    }

    private void setupDefaultAuroraCSS() {
        Document.get().getElementsByTagName("head").getItem(0).appendChild(editorStyle);
        Document.get().getElementsByTagName("head").getItem(0).appendChild(sidebarStyle);
        Document.get().getElementsByTagName("head").getItem(0).appendChild(auroraStyle);
        Document.get().getElementsByTagName("head").getItem(0).appendChild(darkModeStyle);

        Document.get().getElementsByTagName("head").getItem(0).appendChild(linkDarkCMStyle);
    }

    private void eventWiring() {
        wireOnViewStateChanged();
        wireStepNumber();
        wireAddLibraryFunction();
        wireStrategySelection();
        wireNightModeSwitch();
    }

    private void wireStepNumber() {
        this.stepNumber.addKeyUpHandler(event -> {
            String input = stepNumber.getText();

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

        this.stepNumber.addBlurHandler(event -> {
            SidebarView.this.stepNumber.setText("" + prevStepNumber);
        });
    }

    private void wireAddLibraryFunction() {
        this.addLibraryItemDialogBox.getNameField().addKeyUpHandler(event -> {
        });

        // SidebarPresenter does validation.
        this.addLibraryItemDialogBox.getAddButton().addClickHandler(event -> {

            if (SidebarView.this.addLibraryItemDialogBox.getNameField().getText().isEmpty()) {
                SidebarView.this.errorMessageDialogBox.setDescription("Please enter a name.");
                SidebarView.this.errorMessageDialogBox.show();
                return;
            } else if  (SidebarView.this.addLibraryItemDialogBox.getFunctionFieldInput().isEmpty()) {
                SidebarView.this.errorMessageDialogBox.setDescription("Please enter a λ-term.");
                SidebarView.this.errorMessageDialogBox.show();
                return;
            }

            SidebarView.this.eventBus.fireEvent(
                    new AddFunctionEvent(
                            SidebarView.this.addLibraryItemDialogBox.getNameField().getText(),
                            SidebarView.this.addLibraryItemDialogBox.getFunctionFieldInput().replace("λ", "\\"),
                            SidebarView.this.addLibraryItemDialogBox.getDescriptionField().getText()));
        });
        this.addFunctionButton.addClickHandler(event -> SidebarView.this.addLibraryItemDialogBox.show());

    }

    private void wireOnViewStateChanged() {
        this.eventBus.addHandler(ViewStateChangedEvent.TYPE, viewStateChangedEvent -> {
            switch (viewStateChangedEvent.getViewState()) {
                case STEP_BEFORE_RESULT_STATE:
                    setEnabledSidebarWidgets(true, true, true, true);
                    break;
                case PAUSED_STATE:
                    setEnabledSidebarWidgets(true, true, true, true);
                    break;
                case RUNNING_STATE:
                    setEnabledSidebarWidgets(false, false, false, false);
                    break;
                case FINISHED_STATE:
                    setEnabledSidebarWidgets(true, true, true, true);
                    break;
                case FINISHED_FINISHED_STATE:
                    setEnabledSidebarWidgets(true, true, true, true);
                    break;
                default:
                    setEnabledSidebarWidgets(true, true, true, true);
            }
        });
    }

    /**
     * Disables or enables Sidebar's widgets.
     *
     * @param stepNumber enable true, disable false
     * @param addFunctionButton enable true, disable false
     * @param strategySelection enable true, disable false
     * @param nightModeSwitch enable true, disable false
     */
    private void setEnabledSidebarWidgets(boolean stepNumber, boolean addFunctionButton, boolean strategySelection,
                                          boolean nightModeSwitch) {
        this.stepNumber.setEnabled(stepNumber);
        this.addFunctionButton.setEnabled(addFunctionButton);
        this.strategySelection.setEnabled(strategySelection);
        this.nightModeSwitch.setEnabled(nightModeSwitch);
    }

    private void wireStrategySelection() {
        this.strategySelection.getCallByName().addValueChangeHandler(
                event -> fireStrategyEvent(event, StrategyType.CALLBYNAME));
        this.strategySelection.getCallByValue().addValueChangeHandler(
                event -> fireStrategyEvent(event, StrategyType.CALLBYVALUE));
        this.strategySelection.getNormalOrder().addValueChangeHandler(
                event -> fireStrategyEvent(event, StrategyType.NORMALORDER));
        // TODO Wire MANUALSELECTION
    }

    private void fireStrategyEvent(ValueChangeEvent<Boolean> event, StrategyType strategyType) {
        if (event.getValue()) {
            SidebarView.this.eventBus.fireEvent(new EvaluationStrategyChangedEvent(strategyType));
        }
    }

    private void wireNightModeSwitch() {
        this.nightModeSwitch.addClickHandler(event -> {
            boolean checked = ((CheckBox) event.getSource()).getValue();
            if (!checked) {
                Document.get().getElementsByTagName("head").getItem(0).appendChild(lightModeStyle);

                Document.get().getElementsByTagName("head").getItem(0).removeChild(darkModeStyle);
                Document.get().getElementsByTagName("head").getItem(0).removeChild(linkDarkCMStyle);

                Document.get().getBody().addClassName("lightMode");
                Document.get().getBody().removeClassName("darkMode");
            } else if (checked) {
                Document.get().getElementsByTagName("head").getItem(0).appendChild(darkModeStyle);
                Document.get().getElementsByTagName("head").getItem(0).appendChild(linkDarkCMStyle);

                Document.get().getElementsByTagName("head").getItem(0).removeChild(lightModeStyle);
                Document.get().getBody().removeClassName("lightMode");
                Document.get().getBody().addClassName("darkMode");
            }

        });
    }

}

