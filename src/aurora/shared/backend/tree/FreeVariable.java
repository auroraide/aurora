package aurora.shared.backend.tree;

import aurora.shared.backend.visitors.TermVisitor;

/**
 * This class models a free variable. A free variable is not bound by an abstraciton.
 * Every free variable has a name , which can only be lower case letters.
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
