package aurora.client.event;

import aurora.client.view.ViewState;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents an event, which is triggered, when the view is about to change its state.
 */
public class ViewStateChangedEvent extends GwtEvent<ViewStateChangedEventHandler> {
    public static GwtEvent.Type<ViewStateChangedEventHandler> TYPE = new GwtEvent.Type<>();
    private ViewState viewState;


    /**
     * Constructs ViewStateChangedEvent with {@link ViewState}.
     *
     * @param viewState The {@link ViewState}.
     */
    public ViewStateChangedEvent(ViewState viewState) {
        this.viewState = viewState;
    }


    @Override
    public Type<ViewStateChangedEventHandler> getAssociatedType() {
        return this.TYPE;
    }

    @Override
    protected void dispatch(ViewStateChangedEventHandler handler) {
        handler.onViewStateChanged(this);
    }

    /**
     * Get ViewState.
     *
     * @return Return {@link ViewState}.
     */
    public ViewState getViewState() {
        return this.viewState;
    }
}
