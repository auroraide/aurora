package aurora.shared.backend.parser.exceptions;

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

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
