package aurora.client.view.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class AddFunctionDialogBox extends DialogBox {
    interface AddFunctionDialogBoxUiBinder extends UiBinder<Widget, AddFunctionDialogBox> {
    }

    private static AddFunctionDialogBoxUiBinder ourUiBinder = GWT.create(AddFunctionDialogBoxUiBinder.class);

    @UiField TextBox nameField;
    @UiField TextBox functionField;
    @UiField TextArea descriptionField;
    @UiField Button addButton;
    @UiField Button cancelButton;

    public AddFunctionDialogBox() {

        setWidget(ourUiBinder.createAndBindUi(this));
        setAutoHideEnabled(true);
        setText("ADD NEW FUNCTION");
        setGlassEnabled(true);
        center();
        hide();
    }

    @UiHandler("addButton")
    void onAddButtonClicked(ClickEvent event) {
        // TODO More to implement. E.g. clearing TextBoxes.
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