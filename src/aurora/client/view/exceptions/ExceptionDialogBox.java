package aurora.client.view.exceptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class ExceptionDialogBox extends DialogBox {
    interface ExceptionDialogBoxUiBinder extends UiBinder<Widget, ExceptionDialogBox> {
    }

    private static ExceptionDialogBoxUiBinder exceptionUiBinder = GWT.create(ExceptionDialogBoxUiBinder.class);
    

    public ExceptionDialogBox() {
       setWidget(exceptionUiBinder.createAndBindUi(this));
       setAutoHideEnabled(true);
       setText("ERROR");
       setGlassEnabled(true);
       center();
       hide();
    }
}
