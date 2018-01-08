package aurora.backend.betareduction.visitors;

import aurora.backend.TreePath;
import aurora.backend.TreePath.Direction;
import aurora.backend.tree.*;
import aurora.backend.TermVisitor;

import java.util.Iterator;

/**
 * Visitor that allows replacing an Application with an arbitrary Term.
 */
public class ReplaceVisitor implements TermVisitor<Term> {

    private final Term with;

    private final Iterator<Direction> pathIterator;

    /**
     * Constructor that initializes the ReplaceVisitor.
     *
     * @param path Location of the Application to be replaced.
     * @param with The Term that the Application shall be replaced with.
     */
    public ReplaceVisitor(TreePath path, Term with) {
        this.with = with;
        this.pathIterator = path.iterator();
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
