package aurora.backend.parser.exceptions;

import aurora.backend.parser.Token;

/**
 * Thrown in case of a semantic error.
 */
public class SemanticException extends Exception {

    private final int line;

    private final int column;

    private final int offset;

    public SemanticException() {
        this("", 0, 0, 0);
    }

    public SemanticException(String message) {
        this(message, 0, 0, 0);
    }

    /**
     * Construct a {@link SemanticException} using a message, a line number,
     * a column number, and a {@link Token} offset.
     *
     * @param message Custom message.
     * @param line    Line number within code where the semantic error occurred.
     * @param column  Column number within code where the semantic error occurred.
     * @param offset  Offset of the failing {@link Token} within {@link Token} stream where the semantic error occurred.
     */
    public SemanticException(String message, int line, int column, int offset) {
        super(message);
        this.line = line;
        this.column = column;
        this.offset = offset;
    }

    /**
     * Get line within code where the semantic error occurred.
     *
     * @return The line of the semantic error.
     */
    public int getLine() {
        return this.line;
    }

    /**
     * Get column within code where the semantic error occurred.
     *
     * @return The column of the semantic error.
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Get {@link Token} offset within {@link Token} stream where the semantic error occurred.
     *
     * @return The column of the semantic error.
     */
    public int getOffset() {
        return this.offset;
    }

}
