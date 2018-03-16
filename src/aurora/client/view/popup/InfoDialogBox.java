package aurora.client.view.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class InfoDialogBox extends DialogBox {
    private static InfoDialogBoxUiBinder uiBinder = GWT.create(InfoDialogBoxUiBinder.class);
    @UiField
    Label descriptionLabel;

    /**
     * Build a new InfoDialogBox.
     * Enables displaying different kind of information to the user.
     */
    public InfoDialogBox() {
        this.setStyleName("infoDialogBox");
        setWidget(uiBinder.createAndBindUi(this));
        setAutoHideEnabled(true);
        setGlassEnabled(true);
        center();
        hide();
    }

    /**
     * Setter for title.
     *
     * @param title Sets the title.
     */
    public void setTitle(String title) {
        super.setTitle(title);
    }

    /**
     * Setter for description.
     *
     * @param description Sets the description.
     */
    public void setDescription(String description) {
        this.descriptionLabel.setText(description);
    }

    interface InfoDialogBoxUiBinder extends UiBinder<Widget, InfoDialogBox> {
    }

}
