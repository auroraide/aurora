package aurora.client.presenter;

import com.google.gwt.event.shared.EventBus;

public class AuroraPresenter {

    public AuroraPresenter(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public interface Display{

    }

    private EventBus eventBus;

    private void bind() {

    }



}
