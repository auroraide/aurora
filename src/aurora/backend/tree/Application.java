package aurora.backend.tree;

import aurora.backend.TermVisitor;

/**
 * An Application has a left and a right {@link Term}.
 * Only Applications can be Redexes.
 */
public class Application extends Term {

    public final Term left;

    public final Term right;

    /**
     * Initialized with a right and a left Term.
     *
     * @param left  The left Term.
     * @param right The right Term.
     */
    public Application(Term left, Term right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public <T> T accept(TermVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
