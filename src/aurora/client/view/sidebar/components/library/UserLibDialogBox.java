package aurora.client.view.sidebar.components.library;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;

public class UserLibDialogBox {
    interface UserLibDialogBoxUiBinder extends UiBinder<DivElement, UserLibDialogBox> {
    }

    private static UserLibDialogBoxUiBinder ourUiBinder = GWT.create(UserLibDialogBoxUiBinder.class);

    public UserLibDialogBox() {
        DivElement rootElement = ourUiBinder.createAndBindUi(this);
    }
}