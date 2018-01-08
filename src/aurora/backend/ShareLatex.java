package aurora.backend;

/**
 * This class generates a latex snippet which can be pasted into a latex document.
 */
public class ShareLatex {
    private HighlightedLambdaExpression hle;

    /**
     * Traverse the HighlightedLambdaExpression.
     * @param hle The highlighted lambda expression.
     */
    public ShareLatex(HighlightedLambdaExpression hle) {
        this.hle = hle;
    }

    /**
     * Creates a latex snippet as a string which can be copied into a latex document.
     *
     * @return A string which contains the latex code.
     */
    public String generateLatex() {
        return null;
    }
}
