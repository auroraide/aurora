package aurora.client.view.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class ShareLaTeXDialogBox extends DialogBox {
    interface ShareLaTeXDialogBoxUiBinder extends UiBinder<Widget, ShareLaTeXDialogBox> {
    }

    private static ShareLaTeXDialogBoxUiBinder ourUiBinder = GWT.create(ShareLaTeXDialogBoxUiBinder.class);

    @UiField TextArea shareText;
    @UiField Button copyToClipboardButton;
    @UiField Button cancelButton;

    /**
     * Builds a new ShareLaTeXDialogBox.
     * A popup containing the exportable LaTeX snippet.
     */
    public ShareLaTeXDialogBox(String captionText) {

        setWidget(ourUiBinder.createAndBindUi(this));
        setAutoHideEnabled(true);
        setText(captionText);
        setGlassEnabled(true);
        center();
        hide();
    }

    /**
     * Executes once the copyToClipboard {@link Button} is pressed.
     */
    @UiHandler("copyToClipboardButton")
    void onCopyToClipboardButtonClicked(ClickEvent event) {
        hide();
    }

    /**
     * Executes once the cancelButton is pressed.
     */
    @UiHandler("cancelButton")
    void onCancelButtonClicked(ClickEvent event) {
        // TODO Clear TextArea
        hide();
    }

    /**
     * Getter for copyToClipboardButton
     *
     * @return The copyToClipBoard {@link Button}.
     */
    public Button getCopyToClipboardButton() {
        return copyToClipboardButton;
    }

    /**
     * Sets the text to be displayed in {@link TextArea}.
     *
     * @param shareText The text to be displayed.
     */
    public void setShareText(String shareText) {
        this.shareText.setText(shareText);
    }
}
