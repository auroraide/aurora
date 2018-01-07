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
	 * The constructor initializes an empty list.
	 */
    public HighlightedLambdaExpression() {
        this.tokens = new LinkedList<>();
    }

    /**
     * This constructor gets a Term and turns it into a highlighted lambda expression.
     * @param t The given term.
     */
    public HighlightedLambdaExpression(Term t) {
        this();
        t.accept(new TermToHighlightedLambdaExpressionVisitor());
    }

    /**
     * This method adds a token to the token list
     */
    public void appendToken(Token t) {
        this.tokens.add(t);
    }

    @Override
    public Iterator<Token> iterator() {
        return this.tokens.iterator();
    }

    /**
     * This enum contains all token types
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
     * This is a nested class that is used in the token list.
     */
    public class Token {

        private TokenType type;

        private String name;

        /**
         * This constructor gets a type and a name of the token.
         * @param type The type of the token.
         * @param name The name of the token.
         */
        public Token(TokenType type, String name) {
        }

        /**
         * This constructor uses an empty name.
         * @param type The type of the token.
         */
        public Token(TokenType type) {
            this(type, "");
        }

        /**
         * Standard getter.
         * @return The type of the token.
         */
        public TokenType getType() {
            return this.type;
        }

        /**
         * Standard getter.
         * @return The name of the token.
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
