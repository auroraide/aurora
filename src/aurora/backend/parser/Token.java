package aurora.backend.parser;

/**
 * A single concrete token, as produced by {@link LambdaLexer} and consumed by {@link LambdaParser}.
 */
public class Token {

    /**
     * Token types.
     */
    public enum TokenType {
        T_LAMBDA,
        T_DOT,
        T_VARIABLE,
        T_LEFT_PARENS,
        T_RIGHT_PARENS,
        T_FUNCTION,
        T_NUMBER,
        T_COMMENT,
        T_WHITESPACE
    }

    private final TokenType type;

    private final String name;

    private final int line;

    private final int column;

    private final int offset;

    /**
     * Constructor, that creates a new {@link Token}.
     *
     * @param type The type of the Token.
     * @param name The name of the Token.
     * @param line The line number within the code.
     * @param column The column number within the code.
     * @param offset The offset within the {@link Token} list.
     */
    public Token(TokenType type, String name, int line, int column, int offset) {
        this.type = type;
        this.name = name;
        this.line = line;
        this.column = column;
        this.offset = offset;
    }

    /**
     * Constructor with omitted name.
     *
     * @param type The type of the Token.
     * @param line The line number within the code.
     * @param column The column number within the code.
     * @param offset The offset within the {@link Token} list.
     */
    public Token(TokenType type, int line, int column, int offset) {
        this(type, "", line, column, offset);
    }

    /**
     * Get type of this {@link Token}.
     *
     * @return The type of this {@link Token}.
     */
    public TokenType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        switch (this.type) {
            case T_LAMBDA:
                return "\\";
            case T_DOT:
                return ".";
            case T_LEFT_PARENS:
                return "(";
            case T_RIGHT_PARENS:
                return ")";
            case T_FUNCTION:
                return "$" + this.name;
            case T_NUMBER:
                return "c" + this.name;
            case T_COMMENT:
                return "#" + this.name;
        }
        return this.name;
    }

}
