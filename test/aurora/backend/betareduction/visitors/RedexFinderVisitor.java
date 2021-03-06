package aurora.backend.betareduction.visitors;

import aurora.backend.RedexPath;
import aurora.backend.TermVisitor;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;

import java.util.ArrayList;
import java.util.List;

/**
 * Visitor that traverses the Term tree and identifies redexes.
 */
public class RedexFinderVisitor extends TermVisitor<Void> {

    private List<RedexPath> redexes;

    private RedexPath currentPath;

    /**
     * Standard constructor, that initializes an empty RedexFinderVisitor.
     */
    public RedexFinderVisitor() {
        redexes = new ArrayList<>();
        currentPath = new RedexPath();
    }

    /**
     * Get list of found redexes.
     *
     * @return The list of RedexPath objects that describe the locations of the redexes that have been found.
     */
    public List<RedexPath> getResult() {
        return redexes;
    }

    @Override
    public Void visit(Abstraction abs) {
        return abs.body.accept(this);
    }

    @Override
    public Void visit(Application app) {
        app.left.accept(new AbstractionFinder());

        currentPath.push(RedexPath.Direction.LEFT);
        app.left.accept(this);
        currentPath.pop();

        currentPath.push(RedexPath.Direction.RIGHT);
        app.right.accept(this);
        currentPath.pop();

        return null;
    }

    @Override
    public Void visit(BoundVariable bvar) {
        return null;
    }

    @Override
    public Void visit(FreeVariable fvar) {
        return null;
    }

    @Override
    public Void visit(Function function) {
        Term t = function.term;
        t.accept(this);
        return null;
    }

    @Override
    public Void visit(ChurchNumber c) {
        Abstraction abs = c.getAbstraction();
        abs.accept(this);
        return null;
    }

    /**
     * Visitor that helps find abstractions inside our Term tree.
     */
    private class AbstractionFinder extends TermVisitor<Void> {

        @Override
        public Void visit(Abstraction abs) {
            redexes.add(currentPath.deepCopy());
            return null;
        }

        @Override
        public Void visit(Application app) {
            return null;
        }

        @Override
        public Void visit(BoundVariable bvar) {
            return null;
        }

        @Override
        public Void visit(FreeVariable fvar) {
            return null;
        }

        @Override
        public Void visit(Function function) {
            Term t = function.term;
            t.accept(this);
            return null;
        }

        @Override
        public Void visit(ChurchNumber c) {
            redexes.add(currentPath);
            return null;
        }

    }

}
