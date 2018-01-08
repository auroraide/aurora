package aurora.client.view.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class ShortLinkDialogBox extends DialogBox {
    interface ShortLinkDialogBoxUiBinder extends UiBinder<Widget, ShortLinkDialogBox> {
    }

    private static ShortLinkDialogBoxUiBinder ourUiBinder = GWT.create(ShortLinkDialogBoxUiBinder.class);

    @UiField TextArea shortLink;
    @UiField Button copyToClipboard;
    @UiField Button cancelButton;

    public ShortLinkDialogBox() {

        setWidget(ourUiBinder.createAndBindUi(this));
        setAutoHideEnabled(true);
        setText("ShortLink");
        setGlassEnabled(true);
        center();
        hide();
    }

    public void setShortLink(String shortLink) {
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
