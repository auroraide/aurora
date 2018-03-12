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
import com.google.gwt.event.logical.shared.ValueChangeEvent;
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
    private EventBus eventBus;
    private int prevStepNumber = 1;
    private ArrayList<String> userlib;

    final AddLibraryItemDialogBox addLibraryItemDialogBox;
    private final InfoDialogBox errorMessageDialogBox;

    final MenuBar languageMenu;
    final MenuBar shareMenu;
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
    FlowPanel languageButton;
    @UiField
    FlowPanel shareButton;
    @UiField
    StackLayoutPanel stackLibraries;

    LinkElement linkDarkModeAuroraStyle;
    LinkElement linkLightModeAuroraStyle;

    LinkElement linkDarkModeSidebarStyle;
    LinkElement linkLightModeSidebarStyle;

    LinkElement linkDarkModeEditorStyle;
    LinkElement linkLightModeEditorStyle;

    LinkElement linkLightCMStyle;
    LinkElement linkDarkCMStyle;


    /**
     * Created the Sidebar.
     * In addition the add and remove library entry buttons are generated and added to the bar.
     */
    public SidebarView(EventBus eventBus) {
        createCssLinks();
        this.eventBus = eventBus;
        initWidget(ourUiBinder.createAndBindUi(this));
        this.stepNumber.setText(1 + "");
        this.addLibraryItemDialogBox = new AddLibraryItemDialogBox();
        this.errorMessageDialogBox = new InfoDialogBox();
        this.languageMenu = new MenuBar(true);
        this.shareMenu = new MenuBar(true);
        this.userlib = new ArrayList<>();
        setupShareLanguageMenu();
        stackLibraries.showWidget(1);
        this.nightModeSwitch.setValue(true);
        this.nightModeSwitch.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                boolean checked = ((CheckBox) event.getSource()).getValue();
                if (!checked) {
                    Document.get().getElementsByTagName("head").getItem(0).appendChild(linkLightModeAuroraStyle);
                    Document.get().getElementsByTagName("head").getItem(0).appendChild(linkLightModeSidebarStyle);
                    Document.get().getElementsByTagName("head").getItem(0).appendChild(linkLightModeEditorStyle);

                    Document.get().getElementsByTagName("head").getItem(0).removeChild(linkDarkModeAuroraStyle);
                    Document.get().getElementsByTagName("head").getItem(0).removeChild(linkDarkModeSidebarStyle);
                    Document.get().getElementsByTagName("head").getItem(0).removeChild(linkDarkModeEditorStyle);
                    Document.get().getElementsByTagName("head").getItem(0).removeChild(linkDarkCMStyle);

                    Document.get().getBody().addClassName("lightMode");
                    Document.get().getBody().removeClassName("darkMode");
                } else if (checked) {
                    Document.get().getElementsByTagName("head").getItem(0).removeChild(linkLightModeAuroraStyle);
                    Document.get().getElementsByTagName("head").getItem(0).removeChild(linkLightModeSidebarStyle);
                    Document.get().getElementsByTagName("head").getItem(0).removeChild(linkLightModeEditorStyle);

                    Document.get().getElementsByTagName("head").getItem(0).appendChild(linkDarkModeAuroraStyle);
                    Document.get().getElementsByTagName("head").getItem(0).appendChild(linkDarkModeSidebarStyle);
                    Document.get().getElementsByTagName("head").getItem(0).appendChild(linkDarkModeEditorStyle);
                    Document.get().getElementsByTagName("head").getItem(0).appendChild(linkDarkCMStyle);
                    Document.get().getBody().removeClassName("lightMode");
                    Document.get().getBody().addClassName("darkMode");
                }

            }
        });
        eventWiring();
        Document.get().getElementsByTagName("head").getItem(0).appendChild(linkDarkModeAuroraStyle);
        Document.get().getElementsByTagName("head").getItem(0).appendChild(linkDarkModeSidebarStyle);
        Document.get().getElementsByTagName("head").getItem(0).appendChild(linkDarkModeEditorStyle);
        Document.get().getElementsByTagName("head").getItem(0).appendChild(linkDarkCMStyle);

    }

    private void createCssLinks() {
        linkDarkModeAuroraStyle = Document.get().createLinkElement();
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

        linkDarkCMStyle = Document.get().createLinkElement();
        linkDarkCMStyle.setHref("css/CodeMirror/material.css");
        linkDarkCMStyle.setRel("stylesheet");


    }


    private void setupShareLanguageMenu() {
        // sets up language menu
        languageMenu.setAnimationEnabled(false);
        languageMenu.addStyleName("languageButton");
        languageMenu.addItem("language", createLanguageMenuBar());
        this.languageButton.add(languageMenu);

        // sets up share menu
        languageMenu.setAnimationEnabled(false);
        languageMenu.addStyleName("shareButton");
        languageMenu.addItem(" ", createShareMenuBar());
        this.shareButton.add(shareMenu);
    }

    private MenuBar createShareMenuBar() {
        MenuBar shareMenuBar = new MenuBar(true);
        shareMenuBar.addStyleName("shareMenuBar");
        shareMenuBar.addItem("LaTeX", (Command) () -> SidebarView.this.eventBus.fireEvent(new ExportLaTeXAllEvent()));
        shareMenuBar.addItem("Link", (Command) () -> SidebarView.this.eventBus.fireEvent(new ShareLinkAllEvent()));
        return shareMenuBar;
    }

    private MenuBar createLanguageMenuBar() {
        MenuBar languageMenuBar = new MenuBar(true);
        languageMenuBar.addStyleName("languageMenuBar");
        languageMenuBar.addItem("RU", (Command) () -> Window.Location.assign("https://aurora.younishd.fr/?locale=ru"));
        languageMenuBar.addItem("ENG", (Command) () -> Window.Location.assign("https://aurora.younishd.fr/"));
        languageMenuBar.addItem("DE", (Command) () -> Window.alert("hhh"));
        return languageMenuBar;
    }

    private void eventWiring() {
        wireOnViewStateChanged();
        wireStepNumber();
        wireAddLibraryFunction();
        wireStrategySelection();
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

    private void wireOnViewStateChanged() {
        this.eventBus.addHandler(ViewStateChangedEvent.TYPE, viewStateChangedEvent -> {
            switch (viewStateChangedEvent.getViewState()) {
                case STEP_BEFORE_RESULT_STATE:
                    setEnabledSidebarWidgets(true, false, true, true);
                    break;
                case PAUSED_STATE:
                    setEnabledSidebarWidgets(true, false, true, true);
                    break;
                case RUNNING_STATE:
                    setEnabledSidebarWidgets(false, false, false, false);
                    break;
                case FINISHED_STATE:
                    setEnabledSidebarWidgets(true, false, true, true);
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
        //this.nightModeSwitch.setEnabled(nightModeSwitch);
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

    @Override
    public void closeAddLibraryItemDialog() {
        this.addLibraryItemDialogBox.clearAddLibraryItemDialogBox();
        this.addLibraryItemDialogBox.hide();
    }

    @Override
    public void displayAddLibraryItemSyntaxError(SyntaxException error) {
        Window.alert("Syntax error at col: " + error.getColumn() + " !");
    }

    @Override
    public void displayAddLibraryItemSemanticError(SemanticException error) {
        Window.alert("Semantic error at col: " + error.getColumn() + " !");
    }

    @Override
    public void displayAddLibraryItemNameAlreadyTaken() {
        Window.alert("Function name is already taken!");
    }

    @Override
    public void displayAddLibraryItemInvalidName() {
        Window.alert("Function name is incorrect!");
    }

    @Override
    public void displayErrorMessage(String errorMessage) {
        this.errorMessageDialogBox.setDescription(errorMessage);
        this.errorMessageDialogBox.show();
    }

    @Override
    public void addUserLibraryItem(String name, String description) {
        int row = userLibraryTable.getRowCount();
        this.userlib.add(name);
        this.userLibraryTable.setText(row, 0, "$" + name);
        this.userLibraryTable.setText(row, 1, description);

        Button removeLibraryItemButton = new Button("x");
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


}

