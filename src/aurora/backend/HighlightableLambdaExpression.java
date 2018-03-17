package aurora.backend;

import aurora.backend.parser.Token;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Encapsulates the lambda term combined with meta information about highlighting.
 */
public class HighlightableLambdaExpression implements HighlightedLambdaExpression {
    private List<Token> tokens;
    private List<Redex> redexes;
    private Redex next;

    /**
     * Standard constructor that initializes with an empty {@link Token} list.
     */
    public HighlightableLambdaExpression() {
        this.tokens = new LinkedList<>();
        redexes = new LinkedList<>();
    }

    /**
     * Create a {@link HighlightableLambdaExpression} from a stream of {@link Token}s.
     *
     * @param stream The {@link Token} stream.
     */
    public HighlightableLambdaExpression(List<Token> stream) {
        // deep copy
        this.tokens = new ArrayList<>(stream);
        redexes = new LinkedList<>();
    }

    /**
     * Create a {@link HighlightableLambdaExpression} from a {@link Term}.
     *
     * @param t The {@link Term}.
     */
    public HighlightableLambdaExpression(Term t) {
        this();
        t.accept(new FindAbsForAlpha()).accept(new TermToHighlightedLambdaExpressionVisitor());
    }

    /**
     * Create a {@link HighlightableLambdaExpression} from a {@link Term} and select a next redex.
     *
     * @param t Term.
     * @param next Next.
     */
    public HighlightableLambdaExpression(Term t, RedexPath next) {
        this();
        t.accept(new FindAbsForAlpha()).accept(new TermToHighlightedLambdaExpressionVisitor(next));
    }

    /**
     * Create a {@link HighlightableLambdaExpression} from a {@link Token} stream and a corresponding {@link Term}
     * and select a next redex.
     *
     * @param stream Token stream.
     * @param t Term.
     * @param next Next.
     */
    public HighlightableLambdaExpression(List<Token> stream, Term t, RedexPath next) {
        this(stream);
        t.accept(new RedexPathToRedexFromMetaTermVisitor(next));
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
    public Iterator<Token> iterator() {
        return this.tokens.iterator();
    }

    @Override
    public Redex getNextRedex() {
        return next;
    }

    @Override
    public List<Redex> getAllRedexes() {
        return redexes;
    }



    @SuppressWarnings("Duplicates")
    private class RedexPathToRedexFromMetaTermVisitor extends TermVisitor<Void> {
        private final RedexPath nextPath;
        private final RedexPath currentPath;
        private MetaTerm lastMeta;

        public RedexPathToRedexFromMetaTermVisitor(RedexPath nextPath) {
            this.nextPath = nextPath;
            this.currentPath = new RedexPath();
        }

        public RedexPathToRedexFromMetaTermVisitor() {
            this(null);
        }

        @Override
        public Void visit(Abstraction abs) {
            return abs.body.accept(this);
        }

        @Override
        public Void visit(Application app) {
            assert lastMeta != null;

            boolean amRedex = app.left.accept(new AbstractionFinder());

            Token startToken = lastMeta.token;

            currentPath.push(RedexPath.Direction.LEFT);
            app.left.accept(this);
            currentPath.pop();

            Token middleToken = lastMeta.token;

            currentPath.push(RedexPath.Direction.RIGHT);
            app.right.accept(this);
            currentPath.pop();

            Token lastToken = lastMeta.token;


            if (amRedex) {
                assert (startToken != null && middleToken != null && lastToken != null);
                Redex r = new Redex(startToken.getOffset(), middleToken.getOffset(), lastToken.getOffset(),
                        currentPath.clone());
                redexes.add(r);
                // null means we don't want to highlight a next redex
                if (nextPath != null && currentPath.isSame(nextPath)) {
                    next = r;
                }
            }
            return null;
        }

        @Override
        public Void visit(BoundVariable bvar) {
            assert false;
            return null;
        }

        @Override
        public Void visit(FreeVariable fvar) {
            assert false;
            return null;
        }

        @Override
        public Void visit(Function libterm) {
            return libterm.term.accept(this);
        }

        @Override
        public Void visit(ChurchNumber c) {
            assert false;
            return null;
        }

        @Override
        public Void visit(MetaTerm mt) {
            this.lastMeta = mt;
            return mt.term.accept(this);
        }
    }

    /**
     * Get {@link RedexPath} from {@link Token} instance.
     *
     * @param token The {@link Token} object that shall be looked up.
     * @return {@link RedexPath} pointing to the given {@link Token}.
     */
    public RedexPath getRedexPathFromToken(Token token) {
        Redex smallest = new Redex(-1, Integer.MAX_VALUE, Integer.MAX_VALUE, null);
        for (Redex r : redexes) {
            if (token.getOffset() < r.startToken) {
                continue;
            }

            assert (smallest.startToken != r.startToken);
            if (smallest.startToken < r.startToken) {
                smallest = r;
            }
        }

        return smallest.redex;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Token t : this.tokens) {
            builder.append(t.toString());
        }
        return builder.toString();
    }


    /**
     * Traverse the entire Term and initialize on every Abstraction the rename Abstraction Visitor
     * and the Free Variable Conversion Visitor.
     */
    private class FindAbsForAlpha extends TermVisitor<Term> {
        // this bool is true if alphaconversion with fvar is needed
        boolean chg = false;

        @Override
        public Term visit(Abstraction abs) {
            chg = false;
            Abstraction absWithAbsConversion = new Abstraction(abs.body.accept(new RenameAbsVisitor(abs.name)),
                    abs.name);
            AlphaconversionVisitorFV x = new AlphaconversionVisitorFV(absWithAbsConversion.name);
            Term body = absWithAbsConversion.body.accept(x);
            Abstraction absWithFvConversion;
            if (chg) {
                absWithFvConversion = new Abstraction(body, absWithAbsConversion.name + "_alpha");
                while (chg) {
                    chg = false;
                    x = new AlphaconversionVisitorFV(absWithFvConversion.name);
                    body = absWithFvConversion.body.accept(x);

                    if (chg) {
                        absWithFvConversion = new Abstraction(body, absWithFvConversion.name + "_alpha");
                    }
                }
            } else {
                absWithFvConversion = new Abstraction(body, absWithAbsConversion.name);
            }


            return new Abstraction(absWithFvConversion.body.accept(this), absWithFvConversion.name);

        }

        @Override
        public Term visit(Application app) {
            Term left = app.left.accept(this);
            Term right = app.right.accept(this);
            return new Application(left, right);
        }

        @Override
        public Term visit(BoundVariable bvar) {
            return bvar;
        }

        @Override
        public Term visit(FreeVariable fvar) {
            return fvar;
        }

        @Override
        public Term visit(Function libterm) {
            return libterm;
        }

        @Override
        public Term visit(ChurchNumber c) {
            return c;
        }


        /**
         * If Abstraction A has an Abstraction B in its Body and both have the same name, rename Abstraction B.
         */
        private class RenameAbsVisitor extends TermVisitor<Term> {
            private String name;
            private int counter;

            RenameAbsVisitor(String name) {
                this.name = name;
                counter = 0;
            }

            RenameAbsVisitor(String name, int counter) {
                this.name = name;
                this.counter = counter;
            }

            @Override
            public Term visit(Abstraction abs) {
                if (abs.name.equals(name)) {
                    counter++;
                    int mycounter = counter;
                    String newname = name + Integer.toString(counter);
                    return new Abstraction(abs.body.accept(this), name + Integer.toString(mycounter));
                } else {
                    return new Abstraction(abs.body.accept(this), abs.name);
                }
            }

            @Override
            public Term visit(Application app) {
                Term left = app.left.accept(new RenameAbsVisitor(name, counter));
                Term right = app.right.accept(new RenameAbsVisitor(name, counter));
                return new Application(left, right);
            }

            @Override
            public Term visit(BoundVariable bvar) {
                return bvar;
            }

            @Override
            public Term visit(FreeVariable fvar) {
                return fvar;
            }

            @Override
            public Term visit(Function libterm) {
                return libterm;
            }

            @Override
            public Term visit(ChurchNumber c) {
                return c;
            }
        }

        /**
         * If a Free Variable is in the body of an Abstraction and both names are identical, change the name of the
         * Free Variable to name_alpha.
         */
        private class AlphaconversionVisitorFV extends TermVisitor<Term> {
            private String name;

            public AlphaconversionVisitorFV(String name) {
                this.name = name;
            }


            @Override
            public Term visit(Abstraction abs) {
                return new Abstraction(abs.body.accept(this), abs.name);
            }

            @Override
            public Term visit(Application app) {
                Term left = app.left.accept(new AlphaconversionVisitorFV(name));
                Term right = app.right.accept(new AlphaconversionVisitorFV(name));
                return new Application(left, right);
            }

            @Override
            public Term visit(BoundVariable bvar) {
                return bvar;
            }

            @Override
            public Term visit(FreeVariable fvar) {
                if (fvar.name.equals(name)) {
                    chg = true;

                }
                return fvar;

            }

            @Override
            public Term visit(Function function) {
                return function;
            }

            @Override
            public Term visit(ChurchNumber c) {
                return c;
            }
        }


    }

    /**
     * Compute the {@link HighlightableLambdaExpression} representation of the {@link Term} it is applied on.
     * builds token list of the term.
     */
    private class TermToHighlightedLambdaExpressionVisitor extends TermVisitor<Void> {
        private final RedexPath nextPath;
        private int line;
        private int offset;
        private int column;
        int index;
        private RedexPath currentPath;

        TermToHighlightedLambdaExpressionVisitor() {
            line = 1;
            offset = -1;
            column = 0;
            index = 0;
            currentPath = new RedexPath();
            nextPath = null;
        }

        public TermToHighlightedLambdaExpressionVisitor(RedexPath nextPath) {
            this.nextPath = nextPath;
        }


        @Override
        public Void visit(Abstraction abs) {
            column++;
            offset++;
            tokens.add(new Token(Token.TokenType.T_LAMBDA, line, column, offset));

            int length = abs.name.length();
            column += length;
            offset++;
            tokens.add(new Token(Token.TokenType.T_VARIABLE, abs.name, line, column, offset));

            column++;
            offset++;
            tokens.add(new Token(Token.TokenType.T_DOT, line, column, offset));

            column++;
            offset++;
            tokens.add(new Token(Token.TokenType.T_WHITESPACE, " ", line, column, offset));

            // replace all BoundVariables with Free Variables
            Term t = abs.body.accept(new BoundVariableFinder(abs.name));
            t.accept(this);

            return null;
        }

        @Override
        public Void visit(Application app) {
            boolean amRedex = app.left.accept(new AbstractionFinder());
            // these are used to construct Redex start/end token offsets for the view.
            int startToken;
            int middleToken;
            int lastToken;

            currentPath.push(RedexPath.Direction.LEFT);
            if (app.left.accept(new DetermineIfParenthesisNecessaryOnTheLeft())) {
                column++;
                offset++;
                startToken = offset;
                tokens.add(new Token(Token.TokenType.T_LEFT_PARENS, line, column, offset));
                app.left.accept(this);
                column++;
                offset++;
                tokens.add(new Token(Token.TokenType.T_RIGHT_PARENS, line, column, offset));
            } else {
                startToken = offset;
                app.left.accept(this);
            }
            currentPath.pop();

            column++;
            offset++;
            tokens.add(new Token(Token.TokenType.T_WHITESPACE, " ", line, column, offset));

            currentPath.push(RedexPath.Direction.RIGHT);
            if (app.right.accept(new DetermineIfParenthesisNecessaryOnTheRight())) {
                column++;
                offset++;
                middleToken = offset;
                tokens.add(new Token(Token.TokenType.T_LEFT_PARENS, line, column, offset));
                app.right.accept(this);
                column++;
                offset++;
                tokens.add(new Token(Token.TokenType.T_RIGHT_PARENS, line, column, offset));
            } else {
                middleToken = offset;
                app.right.accept(this);
            }
            currentPath.pop();

            if (amRedex) {
                lastToken = offset;
                assert (startToken >= 0 && middleToken >= 0);
                Redex r = new Redex(startToken, middleToken, lastToken, currentPath.clone());
                redexes.add(r);
                if (nextPath != null && currentPath.isSame(nextPath)) {
                    next = r;
                }
            }
            return null;
        }

        @Override
        public Void visit(BoundVariable bvar) {
            assert false : "found a bvar after turning them into fvars";
            int length = String.valueOf(bvar.index).length();
            column += length;
            offset++;
            tokens.add(new Token(Token.TokenType.T_VARIABLE, String.valueOf(bvar.index), line, column, offset));
            return null;
        }

        @Override
        public Void visit(FreeVariable fvar) {
            int length = fvar.name.length();
            column += length;
            offset++;
            tokens.add(new Token(Token.TokenType.T_VARIABLE, fvar.name, line, column, offset));
            return null;
        }

        @Override
        public Void visit(Function libterm) {
            int length = libterm.name.length();
            column += length;
            offset++;
            tokens.add(new Token(Token.TokenType.T_FUNCTION, libterm.name, line, column, offset));
            return null;
        }

        @Override
        public Void visit(ChurchNumber c) {
            int length = String.valueOf(c.value).length();
            column += length;
            offset++;
            tokens.add(new Token(Token.TokenType.T_NUMBER, String.valueOf(c.value), line, column, offset));
            return null;
        }
    }

    private static class DetermineIfParenthesisNecessaryOnTheLeft extends TermVisitor<Boolean> {
        @Override
        public Boolean visit(Abstraction abs) {
            return true;
        }

        @Override
        public Boolean visit(Application app) {
            return false;
        }

        @Override
        public Boolean visit(BoundVariable bvar) {
            return false;
        }

        @Override
        public Boolean visit(FreeVariable fvar) {
            return false;
        }

        @Override
        public Boolean visit(Function libterm) {
            return libterm.term.accept(this);
        }

        @Override
        public Boolean visit(ChurchNumber c) {
            return false;
        }
    }

    private static class DetermineIfParenthesisNecessaryOnTheRight extends TermVisitor<Boolean> {
        @Override
        public Boolean visit(Abstraction abs) {
            return true;
        }

        @Override
        public Boolean visit(Application app) {
            return true;
        }

        @Override
        public Boolean visit(BoundVariable bvar) {
            return false;
        }

        @Override
        public Boolean visit(FreeVariable fvar) {
            return false;
        }

        @Override
        public Boolean visit(Function libterm) {
            return libterm.term.accept(this);
        }

        @Override
        public Boolean visit(ChurchNumber c) {
            return false;
        }

    }

    /**
     * This visitor removes all bound variables with free variables.
     */
    private static class BoundVariableFinder extends TermVisitor<Term> {
        String name;
        int index;


        BoundVariableFinder(String name) {
            this.name = name;
            index = 1;
        }

        BoundVariableFinder(String name, int index) {
            this.name = name;
            this.index = index;
        }

        @Override
        public Term visit(Abstraction abs) {
            index++;
            return new Abstraction(abs.body.accept(this), abs.name);
        }

        @Override
        public Term visit(Application app) {

            Term left = app.left.accept(new BoundVariableFinder(name, index));
            Term right = app.right.accept(new BoundVariableFinder(name, index));
            return new Application(left, right);
        }

        @Override
        public Term visit(BoundVariable bvar) {
            if (bvar.index == index) {
                return new FreeVariable(name);
            } else {
                return bvar;
            }
        }

        @Override
        public Term visit(FreeVariable fvar) {
            return fvar;
        }

        @Override
        public Term visit(Function function) {
            return function;
        }

        @Override
        public Term visit(ChurchNumber c) {
            return c;
        }
    }


    /**
     * Visitor that helps find abstractions inside our Term tree.
     */
    private static class AbstractionFinder extends TermVisitor<Boolean> {

        @Override
        public Boolean visit(Abstraction abs) {
            return true;
        }

        @Override
        public Boolean visit(Application app) {
            return false;
        }

        @Override
        public Boolean visit(BoundVariable bvar) {
            return false;
        }

        @Override
        public Boolean visit(FreeVariable fvar) {
            return false;
        }

        @Override
        public Boolean visit(Function libterm) {
            return libterm.term.accept(this);
        }

        @Override
        public Boolean visit(ChurchNumber c) {
            return true;
        }
    }
}
