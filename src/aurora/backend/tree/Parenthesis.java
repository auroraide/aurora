package aurora.backend.tree;

import aurora.backend.visitors.TermVisitor;

/**
 * This is a Parenthesis which is around a term.
 */
public class Parenthesis extends Term {
    private final Term inner;

    /**
     * The constructor puts the parenthesis around a term.
     * @param inner The parentheses are around this inner term.
     */
    public Parenthesis(Term inner) {
        this.inner = inner;
    }

    @Override
    public <T> T accept(TermVisitor<T> visitor) {
        return visitor.visit(this);
    }

    /**
     * Return the Term inside the parentheses.
     * @return The Term inside the parentheses.
     */
    public Term getInner() {
        return inner;
    }
}
