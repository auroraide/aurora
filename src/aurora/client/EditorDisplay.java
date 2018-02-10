package aurora.client;

import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;

import java.util.List;

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
     * Displays a syntax error.
     * @param syntaxException Exception as given by LambdaLexer.
     */
    void displaySyntaxError(SyntaxException syntaxException);

    /**
     * Displays a semantic error.
     * @param semanticException Exception as given by LambdaLexer.
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
     * Appends a new list of steps to the current step list.
     *
     * @param highlightedLambdaExpressions Steps to append.
     * @param index index of the first item in the given list.
     */
    void addNextStep(List<HighlightedLambdaExpression> highlightedLambdaExpressions, int index);

    /**
     * Wipes the step list entirely.
     */
    void resetSteps();

    /**
     * Clears the result field.
     */
    void resetResult();

    /**
     * Signals the view that no more steps can be displayed. The only action that can be performed now is reset.
     * @param result Irreducible lambda expression.
     */
    void finishedFinished(HighlightedLambdaExpression result);

    /**
     * Displays the result and locks any outputs. No further steps can be added after calling this.
     *
     * @param highlightedLambdaExpression Result to display.
     */
    void displayResult(HighlightedLambdaExpression highlightedLambdaExpression);
}

