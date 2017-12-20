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
	public void accept(TermVisitor visitor) {

	}

    /**
	 *
	 */
    public int getIndex() {
        return this.index;
    }

}
