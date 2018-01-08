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
    private final Display view;

    public AuroraPresenter(EventBus eventBus, Display view) {
        this.eventBus = eventBus;
        this.view = view;
    }

    private void bind() {
        eventBus.addHandler(AddFunctionEvent.TYPE, this::addFunctionEventHandler);
        // etc...
    }

    private void addFunctionEventHandler(AddFunctionEvent event) {
        // implementation
    }
}
