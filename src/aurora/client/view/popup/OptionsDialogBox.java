package aurora.client.view.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class OptionsDialogBox extends DialogBox {
    interface OptionsDialogBoxUiBinder extends UiBinder<Widget, OptionsDialogBox> {
    }

    private static OptionsDialogBoxUiBinder uiBinder = GWT.create(OptionsDialogBoxUiBinder.class);

    /**
     * Build a new OptionsDialogBox.
     * A small popup containing different kinds of options on what to do with the corresponding input.
     */
    public OptionsDialogBox() {
       setWidget(uiBinder.createAndBindUi(this));
       setAutoHideEnabled(true);
       setText("TITLETEXT");
       setGlassEnabled(true);
       center();
       hide();
    }
}
