package aurora.backend;

import aurora.backend.parser.Token;
import aurora.backend.tree.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Encapsulates the lambda term combined with meta information about highlighting.
 */
public class HighlightedLambdaExpression implements Iterable<Token> {

    private final List<Token> tokens;

    /**
	 * Standard constructor that initializes with an empty {@link Token} list.
	 */
    public HighlightedLambdaExpression() {
        this.tokens = new LinkedList<>();
    }

    /**
     * Constructor that creates a {@link HighlightedLambdaExpression} from a stream of {@link Token}s.
     *
     * @param stream The {@link Token} stream.
     */
    public HighlightedLambdaExpression(List<Token> stream) {
        // deep copy
        this.tokens = null;
    }

    /**
     * Constructor that analyzes a {@link Term} and creates the {@link HighlightedLambdaExpression}.
     *
     * @param t The {@link Term} that gets analyzed.
     */
    public HighlightedLambdaExpression(Term t) {
        this();
        t.accept(new TermToHighlightedLambdaExpressionVisitor());
    }

    @Override
    public Iterator<aurora.backend.parser.Token> iterator() {
        return this.tokens.iterator();
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
     * Compute the {@link HighlightedLambdaExpression} representation of the {@link Term} it is applied on.
     */
    private class TermToHighlightedLambdaExpressionVisitor extends TermVisitor<Void> {

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

    }

}
