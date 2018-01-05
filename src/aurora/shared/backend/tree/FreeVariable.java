package aurora.shared.backend.tree;

import aurora.shared.backend.TermVisitor;

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