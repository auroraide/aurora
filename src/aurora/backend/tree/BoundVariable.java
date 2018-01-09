package aurora.backend.tree;

import aurora.backend.TermVisitor;

/**
 *A bound variable is a variable, which is bound by an abstraction.
 * It is possible to find the matching abstraction to the bounded variable.
 */
public class BoundVariable extends Term {

    public final int index;

    /**
     * This constructor gets an index which points to the matching abstraction. The index is commonly called De-Bruijn index.
     * @param index the De-Bruijn Index.
     */
    public BoundVariable(int index) {
        this.index = index;
    }

    @Override
	public <T> T accept(TermVisitor<T> visitor) {
        return visitor.visit(this);
	}

}
