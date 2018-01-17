package aurora.backend.tree;

import aurora.backend.TermVisitor;

/**
 * A bound variable is bounded by an abstraction.
 * It is possible to find abstraction that binds the varriable.
 */
public class BoundVariable extends Term {

    public final int index;

    /**
     * Initialized with index which points to an abstraction. This index is commonly called De-Bruijn index.
     *
     * @param index The De-Bruijn Index.
     */
    public BoundVariable(int index) {
        this.index = index;
    }

    @Override
    public <T> T accept(TermVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
