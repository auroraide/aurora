package aurora.shared.backend;

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
	@Override
	public void accept(TermVisitor visitor) {

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
