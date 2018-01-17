package aurora.client;

/**
 * The implementation of an Aurora view is being hidden behind the <code>AuroraDisplay</code> interface.
 *
 * <code>AuroraDisplay</code> defines the interface, that the {@link aurora.client.presenter.AuroraPresenter}
 * uses to communicate with the view. <br>
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


}
