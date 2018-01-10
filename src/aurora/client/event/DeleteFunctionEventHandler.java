package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a {@link DeleteFunctionEvent}.
 */
public interface DeleteFunctionEventHandler extends EventHandler {

    /**
     * Called when the user wants to delete a function.
     *
     * @param deleteFunctionEvent User input.
     */
    void onDeleteFunction(DeleteFunctionEvent deleteFunctionEvent);
}
