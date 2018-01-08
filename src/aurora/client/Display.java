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

    /**
     * Closes an add function dialog form.
     */
    void closeAddFunctionDialog();

    /**
     * Adds a new function to the views userlibrary.
     * @param name The name function.
     * @param description The function description.
     */
    void addFunction(String name, String description);

    /**
     * Removes a function from the view's user library
     * @param name The name of the function to be removed.
     */
    void removeFunction(String name);

    /**
     * Sets the content of the code editor, replacing it entirely.
     * @param highlightedLambdaExpression term with syntax highlighting.
     */
    void setInput(HighlightedLambdaExpression highlightedLambdaExpression);

    /**
     *
     * @param stepNumber The step number to be set in the view
     */
    void setStepNumber(int stepNumber);

    /**
     * 
     * @param name 
     * @param description
     */
    void addStdLibFunction(String name, String description);


    // TODO complete list of commands one can send to the view.
}
