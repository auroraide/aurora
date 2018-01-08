package aurora.client.presenter;

import aurora.client.Display;
import aurora.client.event.AddFunctionEvent;
import com.google.gwt.event.shared.EventBus;

/**
 * An important class which receives events and decides how to respond to them.
 * Central part of the Model-View-Presenter architecture.
 */
public class AuroraPresenter {
    private final EventBus eventBus;
    private final Display display;

    public AuroraPresenter(EventBus eventBus, Display display) {
        this.eventBus = eventBus;
        this.display = display;
    }

    private void bind() {
        eventBus.addHandler(AddFunctionEvent.TYPE, this::addFunctionEventHandler);
        // etc...
    }

    private void addFunctionEventHandler(AddFunctionEvent event) {
        // implementation
    }
}
