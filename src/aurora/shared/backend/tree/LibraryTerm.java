package aurora.shared.backend.tree;

import aurora.shared.backend.visitors.TermVisitor;

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
	public <T> T accept(TermVisitor<T> visitor) {
        return visitor.visit(this);
	}

    /**
	 *
	 */
    public String getName() {
        return this.name;
    }

}
