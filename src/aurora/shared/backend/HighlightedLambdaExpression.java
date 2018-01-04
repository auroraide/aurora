package aurora.shared.backend;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Encapsulates the lambda term combined with meta information about highlighting.
 */
public class HighlightedLambdaExpression implements Iterable<HighlightedLambdaExpression.Token> {

    private List<Token> tokens;

    /**
	 *
	 */
    public HighlightedLambdaExpression() {
        this.tokens = new LinkedList<>();
    }

    /**
     *
     */
    public void appendToken(Token t) {
        this.tokens.add(t);
    }

    @Override
    public Iterator<Token> iterator() {
        return this.tokens.iterator();
    }

    /**
     *
     */
    public enum TokenType {
        LAMBDA,
        DOT,
        VARIABLE,
        LEFT_PARENTHESIS,
        RIGHT_PARENTHESIS,
        FUNCTION,
        NUMBER
    }

    /**
     *
     */
    public class Token {

        private TokenType type;

        private String name;

        /**
         *
         * @param type
         * @param name
         */
        public Token(TokenType type, String name) {
        }

        /**
         *
         * @param type
         */
        public Token(TokenType type) {
            this(type, "");
        }

        /**
         *
         * @return
         */
        public TokenType getType() {
            return this.type;
        }

        /**
         *
         * @return
         */
        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            switch (this.type) {
                case LAMBDA:
                    return "\\";
                case DOT:
                    return ".";
                case VARIABLE:
                    return this.name;
                case LEFT_PARENTHESIS:
                    return "(";
                case RIGHT_PARENTHESIS:
                    return ")";
                case FUNCTION:
                    return "$" + this.name;
                case NUMBER:
                    return "c" + this.name;
            }
            return this.name;
        }

    }

    @Override
    public String toString() {
        // mostly for debug purposes
        StringBuilder builder = new StringBuilder();
        for (Token t : this.tokens) {
            builder.append(tokens.toString());
            builder.append(" ");
        }
        return builder.toString();
    }

}
