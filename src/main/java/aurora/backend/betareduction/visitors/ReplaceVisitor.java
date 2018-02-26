package aurora.backend.betareduction.visitors;

import aurora.backend.RedexPath;
import aurora.backend.RedexPath.Direction;
import aurora.backend.TermVisitor;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;

import java.util.Iterator;

/**
 * Visitor that allows replacing an Application with an arbitrary Term.
 */
public class ReplaceVisitor extends TermVisitor<Term> {

    private final Term with;

    private final Iterator<Direction> pathIterator;

    /**
     * Constructor that initializes the ReplaceVisitor.
     *
     * @param path Location of the Application to be replaced.
     * @param with The Term that the Application shall be replaced with.
     */
    public ReplaceVisitor(RedexPath path, Term with) {
        this.with = with;
        this.pathIterator = path.iterator();
    }

    @Override
    public Term visit(Abstraction abs) {
        return new Abstraction(abs.body.accept(this),abs.name);
    }

    @Override
    public Term visit(Application app) {
        if (!pathIterator.hasNext()) {
            return with;
        }
        if (pathIterator.next() == Direction.LEFT) {
            return new Application(
                    app.left.accept(this),
                    app.right
            );
        } else {
            return new Application(
                    app.left,
                    app.right.accept(this)
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
    public Term visit(Function function) {
        Term t = function.term;
        return t.accept(this);
    }

    @Override
    public Term visit(ChurchNumber c) {
        Abstraction abs = c.getAbstraction();
        return new Abstraction(abs.body.accept(this),abs.name);
    }

}
