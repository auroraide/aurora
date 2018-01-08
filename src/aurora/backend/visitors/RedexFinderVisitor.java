package aurora.backend.visitors;


import aurora.backend.TreePath;
import aurora.backend.tree.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Visitor that traverses the Term tree and identifies redexes.
 */
public class RedexFinderVisitor implements TermVisitor<Void> {

    private List<TreePath> redexes;

    private TreePath currentPath;

    /**
     * Standard constructor, that initializes an empty RedexFinderVisitor.
     */
    public RedexFinderVisitor() {
        redexes = new ArrayList<>();
        currentPath = new TreePath();
    }

    /**
     * Get list of found redexes.
     *
     * @return The list of TreePath objects that describe the locations of the redexes that have been found.
     */
    public List<TreePath> getResult() {
        return redexes;
    }

    @Override
    public Void visit(Abstraction abs) {
        return abs.getBody().accept(this);
    }

    @Override
    public Void visit(Application app) {
        app.getLeft().accept(new AbstractionFinder());

        currentPath.push(TreePath.Direction.LEFT);
        app.getLeft().accept(this);
        currentPath.pop();

        currentPath.push(TreePath.Direction.RIGHT);
        app.getRight().accept(this);
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
    public Void visit(LibraryTerm libterm) {
        return null; // TODO you need to descend inside it.
    }

    @Override
    public Void visit(ChurchNumber c) {
        return null;
    }

    @Override
    public Void visit(Parenthesis p) {
        return p.getInner().accept(this);
    }

    /**
     * Visitor that helps find abstractions inside our Term tree.
     */
    private class AbstractionFinder implements TermVisitor<Void> {

        @Override
        public Void visit(Abstraction abs) {
            redexes.add(currentPath);
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
        public Void visit(LibraryTerm libterm) {
            return null;
        }

        @Override
        public Void visit(ChurchNumber c) {
            return null;
        }

        @Override
        public Void visit(Parenthesis p) {
            return p.getInner().accept(this);
        }

    }

}
