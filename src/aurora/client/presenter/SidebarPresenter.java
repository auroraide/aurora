package aurora.client.presenter;

import aurora.client.SidebarDisplay;
import aurora.client.event.StepValueChangedEvent;
import com.google.gwt.event.shared.EventBus;


/**
 * <code>SidebarPresenter</code> is responsible for the presentation logic.
 *
 * <p>It fetches sidebar specific user events and acts upon those
 * by using the backend, which presents the model. <code>Aurora Presenter</code> then updates the view through
 * via the {@link SidebarDisplay}.
 */
public class SidebarPresenter {
    private final EventBus eventBus;
    private final SidebarDisplay sidebarDisplay;


    /**
     * Creates an <code>EditorPresenter</code> with an {@link EventBus} and a {@link SidebarDisplay}.
     *
     * @param eventBus       The event bus.
     * @param sidebarDisplay The {@link SidebarDisplay}
     */
    public SidebarPresenter(EventBus eventBus, SidebarDisplay sidebarDisplay) {
        this.eventBus = eventBus;
        this.sidebarDisplay = sidebarDisplay;
        bind();
    }

    private void bind() {
        eventBus.addHandler(StepValueChangedEvent.TYPE, this::onStepValueChanged);
    }

    private void onStepValueChanged(StepValueChangedEvent stepValueChangedEvent) {
        //sidebarDisplay.setStepNumber(stepValueChangedEvent.getStepNumber());
    }
}
