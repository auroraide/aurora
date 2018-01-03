package aurora.client.view.sidebar.components.library;

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
        hide();
    }

    @UiHandler("cancelButton")
    void onCancelButtonClicked(ClickEvent event) {
        hide();
    }
}