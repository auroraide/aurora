package aurora.client.view.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class InfoDialogBox extends DialogBox {
    interface InfoDialogBoxUiBinder extends UiBinder<Widget, InfoDialogBox> {
    }

    private static InfoDialogBoxUiBinder uiBinder = GWT.create(InfoDialogBoxUiBinder.class);



    @UiField Label titleLabel;
    @UiField Label descriptionLabel;
    
    /**
     * Build a new InfoDialogBox.
     * Enables displaying different kind of information to the user.
     */
    public InfoDialogBox() {
       setWidget(uiBinder.createAndBindUi(this));
       setAutoHideEnabled(true);
       setText("TITLETEXT");
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
        this.titleLabel.setText(title);
    }

    /**
     * Setter for description.
     *
     * @param description Sets the description.
     */
    public void setDescription(String description) {
        this.descriptionLabel.setText(description);
    }

}
