package aurora.backend.parser.exceptions;

import aurora.backend.parser.Token;

/**
 * Thrown in case of a syntax error.
 */
public class SyntaxException extends Exception {

    private final int line;

    private final int column;

    private final int offset;

    public SyntaxException() {
        this("", 0, 0, 0);
    }

    public SyntaxException(String message) {
        this(message, 0, 0, 0);
    }

    /**
     * Construct a {@link SyntaxException} using a message, a line number, a column number, and a {@link Token} offset.
     *
     * @param message Custom message.
     * @param line Line number within code.
     * @param column Column number within code.
     * @param offset Offset of the failing {@link Token}.
     */
    public SyntaxException(String message, int line, int column, int offset) {
        super(message);
        this.line = line;
        this.column = column;
        this.offset = offset;
    }

    /**
     * Get line within code where the syntax error occurred.
     *
     * @return The line of the syntax error.
     */
    public int getLine() {
        return this.line;
    }

    /**
     * Get column within code where the syntax error occurred.
     *
     * @return The column of the syntax error.
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Get {@link Token} offset within {@link Token} stream where the syntax error occurred.
     *
     * @return The column of the syntax error.
     */
    public int getOffset() {
        return this.offset;
    }

}
