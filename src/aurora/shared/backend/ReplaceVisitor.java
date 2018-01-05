package aurora.shared.backend;

import aurora.shared.backend.TreePath.Direction;
import aurora.shared.backend.tree.*;

import java.util.Iterator;

public class ReplaceVisitor implements TermVisitor<Term> {
    private final Iterator<Direction> pathIterator;
    private Term with;

    public ReplaceVisitor(Iterator<Direction> pathIterator, Term with) {
        this.pathIterator = pathIterator;
        this.with = with;
    }

    @Override
    public Term visit(Abstraction abs) {
        return abs.accept(this);
    }

    @Override
    public Term visit(Application app) {
        if (!pathIterator.hasNext()) {
            return with;
        }
        if (pathIterator.next() == Direction.LEFT) {
            return new Application(
                    app.getLeft().accept(this),
                    app.getRight()
            );
        } else {
            return new Application(
                    app.getLeft(),
                    app.getRight().accept(this)
            );
        }
    }

    @Override
    public Term visit(BoundVariable bvar) {
        return bvar;
    }

    @Override
    public Term visit(FreeVariable fvar) {
        return fvar;
    }

    @Override
    public Term visit(LibraryTerm libterm) {
        return libterm;
    }

    @Override
    public Term visit(ChurchNumber c) {
        return c;
    }

    @Override
    public Term visit(Parenthesis p) {
        return p.accept(this);
    }
}
