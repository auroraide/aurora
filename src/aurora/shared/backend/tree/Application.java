package aurora.shared.backend.tree;

import aurora.shared.backend.visitors.TermVisitor;

/**
 * This is the Application class. An application has a left and a right Term.
 * Only Applications can be Redexes.
 */
public class Application extends Term {

	private final Term left;

    private final Term right;

    /**
     * The constructor gets a left and a right term.
     * @param left The left term.
     * @param right The right term.
     */
    public Application(Term left, Term right) {
        this.left = left;
        this.right = right;
    }


	public <T> T accept(TermVisitor<T> visitor) {
		//this.left.accept(visitor);
		//this.right.accept(visitor);
		return visitor.visit(this);
	}

    /**
     * A standard getter for the left term of the application.
     * @return The left term of the application.
     */
    public Term getLeft() {
        return this.left;
    }

    /**
     * A standard getter for the right term of the application.
     * @return The right term of the application.
     */
    public Term getRight() {
        return this.right;
    }

}
