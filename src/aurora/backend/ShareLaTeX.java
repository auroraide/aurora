package aurora.backend;

/**
 * This class generates a LaTeX snippet which can be pasted into a LaTeX document.
 */
public class ShareLaTeX {
    private HighlightedLambdaExpression hle;

    /**
     * Traverse the HighlightableLambdaExpression.
     *
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
        String original = hle.toString();
        String latex = "$ ";

        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);
            if (c == '\\') {
                latex += "\\lambda ";
                continue;
            }
            if (c == '$') {
                latex += "\\$ ";
                continue;

            }
            if (c == '_') {
                latex += "\\_ ";
                continue;
            }
            if (c == ' ') {
                latex += " \\ ";
                continue;
            } else {
                latex += original.charAt(i);
            }
        }
        latex += " $";
        return latex;
    }
}
