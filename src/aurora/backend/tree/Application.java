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
     * The constructor gets a left and a right term.
     * @param left The left term.
     * @param right The right term.
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
