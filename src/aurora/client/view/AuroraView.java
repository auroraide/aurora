package aurora.client.view;

import aurora.client.presenter.DesktopPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AuroraView extends Composite implements DesktopPresenter.Display {
    interface DesktopViewUiBinder extends UiBinder<Widget, AuroraView> {
    }

    private static DesktopViewUiBinder ourUiBinder = GWT.create(DesktopViewUiBinder.class);

    public AuroraView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}