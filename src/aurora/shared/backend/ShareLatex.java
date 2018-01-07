package aurora.shared.backend;

/**
 * This class generates a latex snippet which can be pasted into a latex document.
 */
public class ShareLatex {
    private HighlightedLambdaExpression hle;

    /**
     * This constructor gets a highlighted lambda expression so it can traverse the token list.
     * @param hle The highlighted lambda expression.
     */
    public ShareLatex(HighlightedLambdaExpression hle) {
        this.hle = hle;
    }

    /**
     * This method creates a latex snippet as a string which can be copied into a latex document.
     * @return A string which contains the latex code.
     */
    public String generateLatex() {
        return null;
    }
}
