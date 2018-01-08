package aurora.shared.backend;

import aurora.shared.backend.tree.*;
import aurora.shared.backend.visitors.TermVisitor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Encapsulates the lambda term combined with meta information about highlighting.
 */
public class HighlightedLambdaExpression implements Iterable<HighlightedLambdaExpression.Token> {

    private List<Token> tokens;

    /**
	 * Standard constructor, that initializes an empty HighlightedLambdaExpression.
	 */
    public HighlightedLambdaExpression() {
        this.tokens = new LinkedList<>();
    }

    /**
     * Constructor that analyzes a Term and creates the HighligtedLambdaExpression
     *
     * @param t the Term that gets analyzed.
     */
    public HighlightedLambdaExpression(Term t) {
        this();
        t.accept(new TermToHighlightedLambdaExpressionVisitor());
    }

    /**
     * Add a Token to the Token list.
     */
    public void appendToken(Token t) {
        this.tokens.add(t);
    }

    @Override
    public Iterator<Token> iterator() {
        return this.tokens.iterator();
    }

    /**
     * Token types.
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
     * A single token.
     */
    public class Token {

        private TokenType type;

        private String name;

        /**
         * Constructor, that creates a new Token.
         *
         * @param type The type of the Token.
         * @param name The name of the Token.
         */
        public Token(TokenType type, String name) {
        }

        /**
         * Constructor with omitted name.
         * 
         * @param type The type of the Token.
         */
        public Token(TokenType type) {
            this(type, "");
        }

        /**
         * Get type.
         *
         * @return The type of this Token.
         */
        public TokenType getType() {
            return this.type;
        }

        /**
         * Get name.
         *
         * @return The name of this Token.
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

    /**
     * This class computes the HighlightedLambdaTerm representation of the Term it is applied on.
     */
    class TermToHighlightedLambdaExpressionVisitor implements TermVisitor<Void> {

        @Override
        public Void visit(Abstraction abs) {
            return null;
        }

        @Override
        public Void visit(Application app) {
            return null;
        }

        @Override
        public Void visit(BoundVariable bvar) {
            return null;
        }

        @Override
        public Void visit(FreeVariable fvar) {
            return null;
        }

        @Override
        public Void visit(LibraryTerm libterm) {
            return null;
        }

        @Override
        public Void visit(ChurchNumber c) {
            return null;
        }

        @Override
        public Void visit(Parenthesis p) {
            return null;
        }

    }

}
