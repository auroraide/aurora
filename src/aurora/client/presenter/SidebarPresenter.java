package aurora.client.presenter;

import aurora.client.SidebarDisplay;
import com.google.gwt.event.shared.EventBus;

public class SidebarPresenter {
    private final EventBus eventBus;
    private final SidebarDisplay sidebarDisplay;

    public SidebarPresenter(EventBus eventBus, SidebarDisplay sidebarDisplay) {
        this.eventBus = eventBus;
        this.sidebarDisplay = sidebarDisplay;
        bind();
    }

    private void bind() {

    }
}
