package aurora.backend.parser.exceptions;

/**
 * * Thrown by Parser in case of a syntax error.
 */
public class SyntaxException extends Exception {

    private int line;

    private int column;

    public SyntaxException() {}

    public SyntaxException(String message) {
        super(message);
    }

    public SyntaxException(String message, int line, int column) {
        super(message);
        this.line = line;
        this.column = column;
    }

    /**
     * Standard getter.
     * @return The line of the syntax error.
     */
    public int getLine() {
        return line;
    }

    /**
     * Standard getter.
     * @return The column of the syntax error
     */
    public int getColumn() {
        return column;
    }

}
