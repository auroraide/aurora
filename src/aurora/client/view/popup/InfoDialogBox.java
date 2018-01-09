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
    
    /**
     * Build a new InfoDialogBox
     */
    public InfoDialogBox() {
       setWidget(uiBinder.createAndBindUi(this));
       setAutoHideEnabled(true);
       setText("TITLETEXT");
       setGlassEnabled(true);
       center();
       hide();
    }
}
