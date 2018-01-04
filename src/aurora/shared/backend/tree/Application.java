package aurora.shared.backend.tree;

import aurora.shared.backend.TermVisitor;

/**
 *
 */
public class Application extends Term {

	private final Term left;

    private final Term right;

	/**
	 *
	 */
    public Application(Term left, Term right) {
        this.left = left;
        this.right = right;
    }

	/**
	 *
	 */
	public <T> T accept(TermVisitor<T> visitor) {
		//this.left.accept(visitor);
		//this.right.accept(visitor);
		return visitor.visit(this);
	}

	/**
	 *
	 */
    public Term getLeft() {
        return this.left;
    }

	/**
	 *
	 */
    public Term getRight() {
        return this.right;
    }

}
