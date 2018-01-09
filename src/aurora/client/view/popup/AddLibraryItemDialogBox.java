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

    public AddLibraryItemDialogBox() {

        setWidget(ourUiBinder.createAndBindUi(this));
        setAutoHideEnabled(true);
        setText("ADD NEW FUNCTION");
        setGlassEnabled(true);
        center();
        hide();
    }

    @UiHandler("cancelButton")
    void onCancelButtonClicked(ClickEvent event) {
        // TODO More to implement. E.g. clearing TextBoxes.
        hide();
    }

    public TextBox getNameField() {
        return nameField;
    }

    public TextBox getFunctionField() {
        return functionField;
    }

    public TextArea getDescriptionField() {
        return descriptionField;
    }

    public Button getAddButton() {
        return addButton;
    }


}