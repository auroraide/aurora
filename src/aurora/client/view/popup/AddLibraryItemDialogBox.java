package aurora.client.view.popup;

import aurora.client.view.editor.CodeMirrorPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class AddLibraryItemDialogBox extends DialogBox {
    private static AddLibraryItemDialogBoxUiBinder ourUiBinder = GWT.create(AddLibraryItemDialogBoxUiBinder.class);
    private CodeMirrorPanel functionFieldCM;

    @UiField
    TextArea nameField;
    @UiField
    FlowPanel functionField;
    @UiField
    TextArea descriptionField;
    @UiField
    Button addButton;
    @UiField
    Button cancelButton;

    /**
     * Builds a new AddLibraryItemDialogBox.
     * Provides an interface to add new functions to the user library.
     */
    public AddLibraryItemDialogBox() {
        this.setStyleName("addLibraryItem");
        setWidget(ourUiBinder.createAndBindUi(this));
        setUpDialogBox();
        createCodeMirror();
    }

    private void setUpDialogBox() {
        setAutoHideEnabled(false);
        //TODO i18n right down there.
        setText("ADD NEW FUNCTION");
        setGlassEnabled(false);
        hide();
    }

    private void createCodeMirror() {
        this.functionFieldCM = new CodeMirrorPanel();
        this.functionFieldCM.ensureDebugId("functionFieldCM");
        this.functionField.add(this.functionFieldCM);
        this.functionField.setSize("100%", "100%");

        Scheduler.get().scheduleDeferred((Command) () -> {
            functionFieldCM.setOption("theme", "material");
            functionFieldCM.setOption("autofocus", true);
            functionFieldCM.setOption("mode", "aurorascript");
            functionFieldCM.setOption("autoCloseBrackets", true);
            functionFieldCM.setOption("matchBrackets", true);
            functionFieldCM.setOption("styleActiveLine", true);
            functionFieldCM.setOption("back2Lambda", null);
            functionFieldCM.setOption("lineWrapping", true);
        });
    }

    /* @UiHandler("functionField")
    void onFunctionFieldKeyPressed(KeyUpEvent event) {
        if (functionField.getText().contains("\\")) {
            final int cursorPosition = functionField.getCursorPos();
            functionField.setText(functionField.getText().replace("\\", "λ"));
            functionField.setCursorPos(cursorPosition);
        }
    }*/

    /* @UiHandler("functionField")
    void onFunctionFieldBlur(BlurEvent event) {
        functionField.getText().replace("\\", "λ");
    }*/

    /**
     * Executes once the cancelButton is pressed.
     *
     * @param event The {@link ClickEvent}
     */
    @UiHandler("cancelButton")
    void onCancelButtonClicked(ClickEvent event) {
        clearAddLibraryItemDialogBox();
        hide();
    }

    /**
     * Clears all AddLibraryItemDialogBox's text fields.
     */
    public void clearAddLibraryItemDialogBox() {
        this.nameField.setText("");
        //this.functionField.setText("");
        this.functionFieldCM.setValue("");
        this.descriptionField.setText("");
    }

    /**
     * Getter for nameField.
     *
     * @return nameField
     */
    public TextArea getNameField() {
        return nameField;
    }

    /**
     * Getter for descriptionField.
     *
     * @return descriptionField
     */
    public TextArea getDescriptionField() {
        return descriptionField;
    }

    /**
     * Getter for addButton.
     *
     * @return addButton
     */
    public Button getAddButton() {
        return addButton;
    }

    /**
     * Gets the function field's input.
     *
     * @return The input string.
     */
    public String getFunctionFieldInput() {
        return functionFieldCM.getValue();
    }

    /**
     * Sets the function field's input.
     *
     * @param input The input to be set.
     */
    public void setFunctionFieldInput(String input) {
        this.functionFieldCM.setValue(input);
    }


    interface AddLibraryItemDialogBoxUiBinder extends UiBinder<Widget, AddLibraryItemDialogBox> {
    }

}

