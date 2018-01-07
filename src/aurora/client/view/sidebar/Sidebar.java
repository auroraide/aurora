package aurora.client.view.sidebar;

import aurora.client.view.sidebar.components.language.selection.LanguageSelection;
import aurora.client.view.sidebar.components.library.AddFunctionDialogBox;
import aurora.client.view.sidebar.components.share.ShareLaTeXDialogBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class Sidebar extends Composite {
    interface SidebarUiBinder extends UiBinder<Widget, Sidebar> {
    }

    private static SidebarUiBinder ourUiBinder = GWT.create(SidebarUiBinder.class);
    private final AddFunctionDialogBox addFunctionDialogBox;
    private final ShareLaTeXDialogBox shareLaTeXDialogBox;
    private final LanguageSelection languageSelection;
    // change the active language to the chosen

    @UiField TextBox stepNumber;
    @UiField Button addFunction;
    @UiField ToggleButton nightModeSwitch;
    @UiField ListBox shareList;
    @UiField ListBox languageList;

    public Sidebar() {

        initWidget(ourUiBinder.createAndBindUi(this));
        //init get languages fromt he language selection (check in properties)
        addFunctionDialogBox = new AddFunctionDialogBox();
	shareLaTeXDialogBox = new ShareLaTeXDialogBox();
        languageSelection = new LanguageSelection();  
    }

    @UiHandler("stepNumber")
    void onStepSettingTyped(KeyDownEvent event) {

    }

    @UiHandler("addFunction")
    void onAddFunctionClicked(ClickEvent event) {
        addFunctionDialogBox.show();
    }

    @UiHandler("shareList")
    void onShareOptionChosen(ClickEvent event) {
        //what does the event have for info?
        shareLaTeXDialogBox.show();
    }

    @UiHandler("languageList")
    void onLangageOptionChosen(ClickEvent event) {

    }
}
