package aurora.backend.parser.exceptions;

/**
 * Thrown by Parser in case of a semantic error.
 */
public class SemanticException extends Exception {

    private int line;

    private int column;

    public SemanticException() {}

    public SemanticException(String message) {
        super(message);
    }

    public SemanticException(String message, int line, int column) {
        super(message);
        this.line = line;
        this.column = column;
    }

    /**
     * Standard getter.
     * @return The line of the semantic error.
     */
    public int getLine() {
        return line;
    }

    /**
     * Standard getter.
     * @return The column of the semantic error.
     */
    public int getColumn() {
        return column;
    }

}
