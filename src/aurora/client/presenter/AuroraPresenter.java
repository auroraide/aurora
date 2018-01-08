package aurora.client.presenter;

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

    /**
     *
     */
    public interface Display {
        /**
         * Generates a pop-up to notify a user about an error.
         * @param message Message to display.
         */
        void displayError(String message); // TODO there's more than one way to display bad results.

        /**
         * Gets the user input String from the code editor.
         * @return User input. Unparsed lambda term.
         */
        String getInput();

        /**
         * Sets the content of the code editor, replacing it entirely.
         * @param input New content.
         */
        void setInput(String input);

        // TODO complete list of commands one can send to the view.
    }

    private void bind() {
        eventBus.addHandler(AddFunctionEvent.TYPE, this::addFunctionEventHandler);
        // etc...
    }

    private void addFunctionEventHandler(AddFunctionEvent event) {
        // implementation
    }
}
