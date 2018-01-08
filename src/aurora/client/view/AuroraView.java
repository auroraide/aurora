package aurora.client.view;

import aurora.client.presenter.AuroraPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AuroraView extends Composite implements AuroraPresenter.Display {
    private EventBus eventBus;

    interface DesktopViewUiBinder extends UiBinder<Widget, AuroraView> {

    }

    private static DesktopViewUiBinder ourUiBinder = GWT.create(DesktopViewUiBinder.class);

    public AuroraView(EventBus eventBus) {
        this.eventBus = eventBus;

        initWidget(ourUiBinder.createAndBindUi(this));
    }


    private void bind() {
        // TODO bind GWT method
    }
}
