package aurora.backend;

/**
 * This class generates a LaTeX snippet which can be pasted into a LaTeX document.
 */
public class ShareLaTeX {
    private HighlightedLambdaExpression hle;

    /**
     * Traverse the HighlightableLambdaExpression.
     * @param hle The highlighted lambda expression.
     */
    public ShareLaTeX(HighlightedLambdaExpression hle) {
        this.hle = hle;
    }

    /**
     * Creates a LaTeX snippet as a string which can be copied into a LaTeX document.
     *
     * @return A string which contains the LaTeX code.
     */
    public String generateLaTeX() {
        return null;
    }
}
