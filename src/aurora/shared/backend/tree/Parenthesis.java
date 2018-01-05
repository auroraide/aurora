package aurora.shared.backend.tree;

import aurora.shared.backend.TermVisitor;

public class Parenthesis extends Term {
    private final Term inner;

    public Parenthesis(Term inner) {
        this.inner = inner;
    }

    @Override
    public <T> T accept(TermVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Term getInner() {
        return inner;
    }
}
