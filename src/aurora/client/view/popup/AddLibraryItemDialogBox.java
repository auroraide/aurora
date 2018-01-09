package aurora.client.view.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class AddLibraryItemDialogBox extends DialogBox {
    interface AddLibraryItemDialogBoxUiBinder extends UiBinder<Widget, AddLibraryItemDialogBox> {
    }

    private static AddLibraryItemDialogBoxUiBinder ourUiBinder = GWT.create(AddLibraryItemDialogBoxUiBinder.class);

    @UiField TextBox nameField;
    @UiField TextBox functionField;
    @UiField TextArea descriptionField;
    @UiField Button addButton;
    @UiField Button cancelButton;

    /**
     * Builds a new AddLibraryItemDialogBox.
     * Provides an interface to add new functions to the user library.
     */
    public AddLibraryItemDialogBox() {

        setWidget(ourUiBinder.createAndBindUi(this));
        setAutoHideEnabled(true);
        setText("ADD NEW FUNCTION");
        setGlassEnabled(true);
        center();
        hide();
    }

    /**
     * Executes once the cancelButton is pressed.
     *
     * @param event The {@link ClickEvent}
     */
    @UiHandler("cancelButton")
    void onCancelButtonClicked(ClickEvent event) {
        // TODO More to implement. E.g. clearing TextBoxes.
        hide();
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
    public TextBox getFunctionField() {
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

}

