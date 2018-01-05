package aurora.client.view.sidebar.components.share;

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

    @UiField TextArea shareLatex;
    @UiField Button copyToClipboard;
    @UiField Button cancelButton;

    public ShareLaTeXDialogBox() {

        setWidget(ourUiBinder.createAndBindUi(this));
        setAutoHideEnabled(true);
        setText("LaTeX export");
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