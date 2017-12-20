package aurora.shared.backend;

/**
 *
 */
public class BoundVariable extends Term {

    private final int index;

    /**
	 *
	 */
    public BoundVariable(int index) {
        this.index = index;
    }

    /**
	 *
	 */
    @Override
	public <T> T accept(TermVisitor<T> visitor) {
        return visitor.visit(this);
	}

    /**
	 *
	 */
    public int getIndex() {
        return this.index;
    }

}
