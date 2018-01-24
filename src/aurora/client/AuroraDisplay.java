package aurora.client;

import aurora.backend.HighlightedLambdaExpression;

/**
 * The implementation of an Aurora view is being hidden behind the <code>AuroraDisplay</code> interface.
 * <p>
 * <code>AuroraDisplay</code> defines the interface, that the {@link aurora.client.presenter.AuroraPresenter}
 * uses to communicate with the view.
 * A view implementing this interface, therefore provides necessary methods for the above mentioned
 * communication purposes regarding the presenter.
 */
public interface AuroraDisplay {
    /**
     * Displays the LaTeX snippet in the dialog for the user to copy.
     *
     * @param latexCode The LaTeX code to copy.
     */
    void displayLatexSnippetDialog(String latexCode);

    /**
     * Displays the url in the dialog for the user to copy.
     *
     * @param shortLink The url to copy.
     */
    void displayShortLinkDialog(String shortLink);

    /**
     * Sets the step number in the AuroraDisplay.
     *
     * @param stepNumber The step number.
     */
    void setStepNumber(int stepNumber);

    /**
     * Displays a syntax error message.
     *
     * @param message Message to display.
     */
    void displaySyntaxError(String message); // TODO there's more than one way to display bad results.

    /**
     * Gets the user input String from the code editor.
     *
     * @return User input. Unparsed lambda term.
     */
    String getInput();

    /**
     * Sets the content of the code editor, replacing it entirely.
     *
     * @param highlightedLambdaExpression term with syntax highlighting.
     */
    void setInput(HighlightedLambdaExpression highlightedLambdaExpression); // TODO umbenenny, staeamynecheine

    /**
     * Appends a new step to the current step list.
     *
     * @param highlightedLambdaExpression Step to append.
     */
    void addNextStep(HighlightedLambdaExpression highlightedLambdaExpression);

    /**
     * Wipes the step list entirely.
     */
    void resetSteps();

    /**
     * Displays the result and locks any outputs. No further steps can be added after calling this.
     *
     * @param highlightedLambdaExpression Result to display.
     */
    void displayResult(HighlightedLambdaExpression highlightedLambdaExpression);

    /**
     * Closes an add function dialog form.
     */
    void closeAddLibraryItemDialog();

    /**
     * Adds a new function to the views user library.
     *
     * @param name        The name function.
     * @param description The function description.
     */
    void addUserLibraryItem(String name, String description);

    /**
     * Removes a function from the view's user library.
     *
     * @param name The name of the function to be removed.
     */
    void removeUserLibraryItem(String name);

    /**
     * Adds a new function to the views' standard library.
     *
     * @param name        The name of the function to be added.
     * @param description The function description.
     */
    void addStandardLibraryItem(String name, String description);

    /**
     * Removes a function form the views' standard library.
     *
     * @param name The name of the function to be removed.
     */
    void removeStandardLibraryItem(String name);


}
