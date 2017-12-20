package aurora.shared.backend;

/**
 *
 */
public class LibraryTerm extends Term {

    private final String name;

    /**
	 *
	 */
    public LibraryTerm(String name) {
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
