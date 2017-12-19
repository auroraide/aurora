package aurora.shared.backend;

public class Application extends Term {
	private final Term left;
    private final Term right;

    public Application(Term left, Term right) {
        this.left = left;
        this.right = right;
    }

    public Term getLeft() {
        return left;
    }

    public Term getRight() {
        return right;
    }

	public Term substitute(int index, Term with) {
	    Term left = this.left.substitute(index + 1, with);
	    Term right = this.right.substitute(index + 1, with);
	    return new Application(left, right);
	}
}
