package aurora.shared.backend;

/**
 *
 */
public class FreeVariable extends Term {

    private final String name;

    /**
	 *
	 */
    public FreeVariable(String name) {
        this.name = name;
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
    public String getName() {
        return this.name;
    }

}
