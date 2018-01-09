package aurora.backend.tree;

import aurora.backend.TermVisitor;

/**
 * Models a free variable. A free variable isn't bound by an abstraction.
 * It has a lower case letters name.
 */
public class FreeVariable extends Term {

    public final String name;

    /**
     * Initialized with a name.
     * @param name The name of the variable.
     */
    public FreeVariable(String name) {
        this.name = name;
    }

    @Override
	public <T> T accept(TermVisitor<T> visitor) {
        return visitor.visit(this);
	}

}
