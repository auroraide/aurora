package aurora.backend;

import aurora.backend.parser.Token;
import aurora.backend.tree.Term;

/**
 * Special kind of {@link Term} that keeps track of meta information about where it came from.
 * A {@link MetaTerm} is a wrapper around a {@link Term} that carries a reference to a concrete {@link Token} object.
 */
public class MetaTerm extends Term {

    /**
     * Inner {@link Term}.
     */
    public final Term term;

    /**
     * Associated {@link Token}.
     */
    public final Token token;

    /**
     * Constructor that initializes a {@link MetaTerm} with an inner {@link Term} and an associated {@link Token}.
     *
     * @param term The inner {@link Term}.
     * @param token The associated {@link Token}.
     */
    public MetaTerm(Term term, Token token) {
        this.term = term;
        this.token = token;
    }

    @Override
    public <T> T accept(TermVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
