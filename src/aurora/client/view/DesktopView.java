package aurora.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import aurora.client.view.infoPopup.InfoDialogBox;

public class DesktopView extends Composite {
    interface DesktopViewUiBinder extends UiBinder<Widget, DesktopView> {
    }

    private static DesktopViewUiBinder ourUiBinder = GWT.create(DesktopViewUiBinder.class);

    public DesktopView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}
