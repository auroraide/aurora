package aurora.client;

import aurora.backend.HighlightedLambdaExpression;

/**
 * Defines possible commands which can be used to modify the view.
 */
public interface Display {
    /**
     * @param message Message to display.
     */
    void displaySyntaxError(String message); // TODO there's more than one way to display bad results.

    void displayWarning(String message);

    /**
     * Gets the user input String from the code editor.
     * @return User input. Unparsed lambda term.
     */
    String getInput();

    void closeAddFunctionDiaglog();

    void addFunction(String name, String description);
    void removeFunction(String name);

    /**
     * Sets the content of the code editor, replacing it entirely.
     * @param highlightedLambdaExpression term with syntax highlighting.
     */
    void setInput(HighlightedLambdaExpression highlightedLambdaExpression);

    // TODO complete list of commands one can send to the view.
}
