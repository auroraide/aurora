package aurora.backend.tree;

import aurora.backend.TermVisitor;

/**
 * Represent the terms of the standard- and the userlibrary.
 * Every LibraryTerm has a name which starts with a "$" and then consists of lower case letters.
 */
public class LibraryTerm extends Term {

    private final String name;

    /**
     * The constructor of the class gets a String (which starts with a $), which is used as the name of the library term.
     * @param name The name of the library term.
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
     * This is a standard getter, it returns the name of the library term.
     * @return the name of the library term as a string.
     */
    public String getName() {
        return this.name;
    }

}
