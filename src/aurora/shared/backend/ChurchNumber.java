package aurora.shared.backend;

/**
 *
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
