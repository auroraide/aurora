package aurora.backend;

import aurora.backend.parser.Token;

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
        StringBuilder sb = new StringBuilder("$");

        for (Token t : hle) {
            switch (t.getType()) {
                case T_LAMBDA:
                    sb.append("\\lambda ");
                    break;
                case T_DOT:
                    sb.append(".");
                    break;
                case T_VARIABLE:
                    sb.append(specialCharacters(t.getName()));
                    break;
                case T_LEFT_PARENS:
                    sb.append("(");
                    break;
                case T_RIGHT_PARENS:
                    sb.append(")");
                    break;
                case T_FUNCTION:
                    sb.append("\\texttt{\\$");
                    sb.append(specialCharacters(t.getName()));
                    sb.append("}");
                    break;
                case T_NUMBER:
                    sb.append("\\textbf{");
                    sb.append(t.getName());
                    sb.append("}");
                    break;
                case T_WHITESPACE:
                    sb.append("\\ ");
                    break;
                default:
                    throw new IllegalStateException("Unknown token type");
            }
        }

        sb.append("$");
        return sb.toString();
    }

    private String specialCharacters(String input) {
        String result = "";
        for (char c : input.toCharArray()) {
            switch (c) {
                case '_':
                    result += "\\_ ";
                    break;
                default:
                    result += c;
            }
        }
        return result;
    }

}
