package aurora.client;

import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;

/**
 * The implementation of {@link aurora.client.view.editor.EditorView} is hidden behind the
 * <code>EditorDisplay</code> interface.
 * <p>
 * <code>EditorDisplay</code> defines the interface, that the {@link aurora.client.presenter.EditorPresenter}
 * uses to communicate with the view.
 * A view implementing this interface, therefore provides necessary methods for the above mentioned
 * communication purposes regarding the presenter.
 */
public interface EditorDisplay {

    /**
     * Displays a syntax error message.
     * @param syntaxException Contains detailed information about the error.
     */
    void displaySyntaxError(SyntaxException syntaxException);

    /**
     * Shows the user they made a semantic error in their input.
     * @param semanticException Contains detailed information about the error.
     */
    void displaySemanticError(SemanticException semanticException);

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
}

