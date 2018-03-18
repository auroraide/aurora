package aurora.client.view.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AddLibraryItemDialogBox extends DialogBox {
    private static AddLibraryItemDialogBoxUiBinder ourUiBinder = GWT.create(AddLibraryItemDialogBoxUiBinder.class);

    @UiField
    TextBox nameField;
    @UiField
    TextArea functionField;
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
    }

    private void setUpDialogBox() {
        setAutoHideEnabled(false);
        //TODO i18n right down there.
        setText("ADD NEW FUNCTION");
        setGlassEnabled(false);
        hide();
    }

    @UiHandler("functionField")
    void onFunctionFieldKeyPressed(KeyUpEvent event) {
        if (functionField.getText().contains("\\")) {
            final int cursorPosition = functionField.getCursorPos();
            functionField.setText(functionField.getText().replace("\\", "λ"));
            functionField.setCursorPos(cursorPosition);
        }
    }

    @UiHandler("functionField")
    void onFunctionFieldBlur(BlurEvent event) {
        functionField.getText().replace("\\", "λ");
    }

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
        this.functionField.setText("");
        this.descriptionField.setText("");
    }

    /**
     * Getter for nameField.
     *
     * @return nameField
     */
    public TextBox getNameField() {
        return nameField;
    }

    /**
     * Getter for functionField.
     *
     * @return functionField
     */
    public TextArea getFunctionField() {
        return functionField;
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

    interface AddLibraryItemDialogBoxUiBinder extends UiBinder<Widget, AddLibraryItemDialogBox> {
    }

}

