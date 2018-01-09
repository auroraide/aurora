package aurora.client.view.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class DeleteLibraryItemDialogBox extends DialogBox {
    interface DeleteLibraryItemDialogBoxUiBinder extends UiBinder<Widget, DeleteLibraryItemDialogBox> {}

    private static DeleteLibraryItemDialogBoxUiBinder ourUiBinder = GWT.create(DeleteLibraryItemDialogBoxUiBinder.class);

    @UiField TextArea messageField;
    @UiField Button submitButton;
    @UiField Button cancelButton;

    /**
     * Build a new DeleteLibraryItemDialogBox.
     * Provides an interface to delete user defined funtions from the user library.
     */
    public DeleteLibraryItemDialogBox() {
        setWidget(ourUiBinder.createAndBindUi(this));
    }

    /**
     * Getter for submit button.
     *
     * @return submitButton
     */
    public Button getSubmitButton() {
        return submitButton;
    }

    /**
     * Executes once the cancelButton is clicked.
     *
     * @param event The {@link ClickEvent}.
     */
    @UiHandler("cancelButton")
    void onCancelButtonClicked(ClickEvent event) {

    }
}

