package aurora.backend;

import aurora.backend.parser.Token;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Encapsulates the lambda term combined with meta information about highlighting.
 */
public class HighlightableLambdaExpression implements HighlightedLambdaExpression {

    private final List<Token> tokens;

    /**
     * Standard constructor that initializes with an empty {@link Token} list.
     */
    public HighlightableLambdaExpression() {
        this.tokens = new LinkedList<>();
    }

    /**
     * Constructor that creates a {@link HighlightableLambdaExpression} from a stream of {@link Token}s.
     *
     * @param stream The {@link Token} stream.
     */
    public HighlightableLambdaExpression(List<Token> stream) {
        // deep copy
        this.tokens = null;
    }

    /**
     * Constructor that analyzes a {@link Term} and creates the {@link HighlightableLambdaExpression}.
     *
     * @param t The {@link Term} that gets analyzed.
     */
    public HighlightableLambdaExpression(Term t) {
        this();
        t.accept(new TermToHighlightedLambdaExpressionVisitor());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HighlightableLambdaExpression tokens1 = (HighlightableLambdaExpression) o;
        return Objects.equals(tokens, tokens1.tokens);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tokens);
    }

    @Override
    public Iterator<aurora.backend.parser.Token> iterator() {
        return this.tokens.iterator();
    }

    @Override
    public Redex getPreviousRedex() {
        return null;
    }

    @Override
    public Redex getNextRedex() {
        return null;
    }

    @Override
    public List<Redex> getAllRedexes() {
        return null;
    }

    /**
     * Get {@link RedexPath} from {@link Token} instance.
     *
     * @param token The {@link Token} object that shall be looked up.
     * @return {@link RedexPath} pointing to the given {@link Token}.
     */
    public RedexPath getRedexPathFromToken(Token token) {
        return null;
    }

    public void highlightRedex(Redex redex) {
    }

    public void highlightPreviousRedex(Redex redex) {
    }

    public void highlightNextRedex(Redex redex) {
    }

    @Override
    public String toString() {
        // mostly for debug purposes
        StringBuilder builder = new StringBuilder();
        for (Token t : this.tokens) {
            builder.append(t.toString());
            builder.append(" ");
        }
        return builder.toString();
    }

    /**
     * Compute the {@link HighlightableLambdaExpression} representation of the {@link Term} it is applied on.
     */
    private class TermToHighlightedLambdaExpressionVisitor extends TermVisitor<Void> {
        int line;
        int offset;
        int column;
        private boolean isapp;
        private boolean isabs;

        TermToHighlightedLambdaExpressionVisitor() {
            line = 1;
            offset = -1;
            column = 0;
        }

        @Override
        public Void visit(Abstraction abs) {

            column++;
            offset++;
            tokens.add(new Token(Token.TokenType.T_LAMBDA,line,column,offset));

            int length = abs.name.length();
            column += length;
            offset++;
            tokens.add(new Token(Token.TokenType.T_VARIABLE,abs.name,line,column,offset));

            column++;
            offset++;
            tokens.add(new Token(Token.TokenType.T_DOT,line,column,offset));
            abs.body.accept(this);

            return null;
        }

        @Override
        public Void visit(Application app) {
            AppAbschecker abschecker = new AppAbschecker();
            app.left.accept(abschecker);
            if (isabs) {
                column++;
                offset++;
                tokens.add(new Token(Token.TokenType.T_LEFT_PARENS, line, column, offset));
                app.left.accept(this);
                column++;
                offset++;
                tokens.add(new Token(Token.TokenType.T_RIGHT_PARENS, line, column, offset));
            } else {
                app.left.accept(this);
            }
            AppAbschecker absappchecker = new AppAbschecker();
            app.right.accept(absappchecker);
            if (isabs || isapp) {
                column++;
                offset++;
                tokens.add(new Token(Token.TokenType.T_LEFT_PARENS, line, column, offset));
                app.right.accept(this);
                column++;
                offset++;
                tokens.add(new Token(Token.TokenType.T_RIGHT_PARENS, line, column, offset));
            } else {
                app.right.accept(this);
            }
            return null;
        }

        @Override
        public Void visit(BoundVariable bvar) {
            int length = String.valueOf(bvar.index).length();
            column += length;
            offset++;
            tokens.add(new Token(Token.TokenType.T_VARIABLE,String.valueOf(bvar.index),line,column,offset));
            /**
             * ALPHACONVERSION AND analyze BOUNDVARIABLENAME TO STring HERE
             */
            return null;
        }

        @Override
        public Void visit(FreeVariable fvar) {
            int length = fvar.name.length();
            column += length;
            offset++;
            tokens.add(new Token(Token.TokenType.T_VARIABLE,fvar.name,line,column,offset));
            return null;
        }

        @Override
        public Void visit(Function libterm) {
            int length = libterm.name.length();
            column += length;
            offset++;
            tokens.add(new Token(Token.TokenType.T_FUNCTION,libterm.name,line,column,offset));
            return null;
        }

        @Override
        public Void visit(ChurchNumber c) {
            int length = String.valueOf(c.value).length();
            column += length;
            offset++;
            tokens.add(new Token(Token.TokenType.T_NUMBER,String.valueOf(c.value),line,column,offset));
            return null;
        }


        private class AppAbschecker extends TermVisitor<Void> {

            AppAbschecker() {
                isapp = false;
                isabs = false;
            }

            @Override
            public Void visit(Abstraction abs) {
                isapp = false;
                isabs = true;
                return null;
            }

            @Override
            public Void visit(Application app) {
                isapp = true;
                isabs = false;
                return null;
            }

            @Override
            public Void visit(BoundVariable bvar) {
                isapp = false;
                isabs = false;
                return null;
            }

            @Override
            public Void visit(FreeVariable fvar) {
                isapp = false;
                isabs = false;
                return null;
            }

            @Override
            public Void visit(Function libterm) {
                Term t  = libterm.term;
                t.accept(this);
                return null;
            }

            @Override
            public Void visit(ChurchNumber c) {
                isapp = false;
                isabs = true;
                return null;
            }
        }


    }



}
