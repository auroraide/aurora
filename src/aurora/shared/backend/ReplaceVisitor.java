package aurora.shared.backend;

import aurora.shared.backend.tree.*;

public class ReplaceVisitor implements TermVisitor<Term> {
    private final TreePath path;
    private Term with;
    private int pathIndex;

    public ReplaceVisitor(TreePath path, Term with) {
        this.path = path;
        this.with = with;
    }

    @Override
    public Term visit(Abstraction abs) {
        return abs.accept(this);
    }

    @Override
    public Term visit(Application app) {
        if (pathIndex == path.getLength()) {
            return with;
        }
        if (path.getDirection(pathIndex) == TreePath.Direction.LEFT) {
            pathIndex++;
            return new Application(
                    app.getLeft().accept(this),
                    app.getRight()
            );
        } else {
            pathIndex++;
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
