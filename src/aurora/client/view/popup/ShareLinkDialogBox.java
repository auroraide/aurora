package aurora.client.view.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class ShareLinkDialogBox extends DialogBox {
    interface ShareLinkDialogBoxUiBinder extends UiBinder<Widget, ShareLinkDialogBox> {
    }

    private static ShareLinkDialogBoxUiBinder ourUiBinder = GWT.create(ShareLinkDialogBoxUiBinder.class);

    @UiField TextArea shareLink;
    @UiField Button copyToClipboard;
    @UiField Button cancelButton;

    public ShareLinkDialogBox() {

        setWidget(ourUiBinder.createAndBindUi(this));
        setAutoHideEnabled(true);
        setText("Link export");
        setGlassEnabled(true);
        center();
        hide();
    }

    @UiHandler("copyToClipboard")
    void onCopyToClipboardButtonClicked(ClickEvent event) {
        hide();
    }

    @UiHandler("cancelButton")
    void onCancelButtonClicked(ClickEvent event) {
        hide();
    }
}