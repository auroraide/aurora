package aurora;

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
}
