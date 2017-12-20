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
	public void accept(TermVisitor visitor) {

	}

    /**
	 *
	 */
    public int getValue() {
        return this.value;
    }

}
