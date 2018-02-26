package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles an {@link AddFunctionEvent}.
 */
public interface AddFunctionEventHandler extends EventHandler {
    /**
     * The notify in the observer pattern.
     *
     * @param event User input.
     */
    void onAddFunction(AddFunctionEvent event);
}
