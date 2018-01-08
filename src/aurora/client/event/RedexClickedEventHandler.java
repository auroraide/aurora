package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface RedexClickedEventHandler extends EventHandler {
    /**
     * Called when the user clicks on a Redex.
     * @param redexClickedEvent Contains the reference to the Redex selected by the user.
     */
    void onRedexClicked(RedexClickedEvent redexClickedEvent);
}
