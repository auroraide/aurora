package aurora.client;

/**
 * Defines possible commands which can be used to modify the view.
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
