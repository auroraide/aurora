package aurora.shared.backend.tree;

import aurora.shared.backend.visitors.TermVisitor;

/**
 * A church number is a representation of a number in lambda calculus.
 */
public class ChurchNumber extends Term {

    private final int value;

    /**
	 *
	 */
    public ChurchNumber(int value) {
        this.value = value;
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
    public int getValue() {
        return this.value;
    }

}
