package aurora.client.view.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class ShareDialogBox extends DialogBox {
    interface ShareDialogBoxUiBinder extends UiBinder<Widget, ShareDialogBox> {}

    private static ShareDialogBoxUiBinder ourUiBinder = GWT.create(ShareDialogBoxUiBinder.class);

    @UiField
    TextArea shareText;
    @UiField
    Button copyToClipboardButton;
    @UiField
    Button closeButton;

    /**
     * Builds a new ShareDialogBox.
     * A popup containing the exportable text snippet.
     */
    public ShareDialogBox(String captionText) {
        this.setStyleName("shareDialogBox");
        setWidget(ourUiBinder.createAndBindUi(this));
        setAutoHideEnabled(true);
        setText(captionText);
        setGlassEnabled(true);
        center();
        hide();
    }

    /**
     * Executes once the cancelButton is pressed.
     */
    @UiHandler("closeButton")
    void onCancelButtonClicked(ClickEvent event) {
        ShareDialogBox.this.shareText.setText("");
        ShareDialogBox.this.hide();
    }

    @UiHandler("copyToClipboardButton")
    void onCopyToClipboardButtonClicked(ClickEvent event) {
        this.shareText.setFocus(true);
        this.shareText.selectAll();
        copyToClipboard();
        ShareDialogBox.this.shareText.setText("");
        ShareDialogBox.this.hide();
    }

    /**
     * Getter for copyToClipboardButton.
     *
     * @return The copyToClipBoard {@link Button}.
     */
    public Button getCopyToClipboardButton() {
        return copyToClipboardButton;
    }

    /**
     * Sets the text to be displayed in the Share {@link TextArea}.
     *
     * @param shareText The text to be displayed.
     */
    public void setShareText(String shareText) {
        this.shareText.setText(shareText);
        this.shareText.setFocus(true);
        this.shareText.selectAll();
    }

    private static native boolean copyToClipboard() /*-{
        return $doc.execCommand('copy');
    }-*/;

}