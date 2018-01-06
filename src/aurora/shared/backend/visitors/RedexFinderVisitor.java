package aurora.shared.backend.visitors;


import aurora.shared.backend.TreePath;
import aurora.shared.backend.tree.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the term visitor and it traverses the tree.
 * It finds all redexes in the traversed term.
 * This is the "Concrete Visitor" in the visitor pattern
 * This class can only be called once.
 */
public class RedexFinderVisitor implements TermVisitor<Void> {
    private List<TreePath> redexes;
    private TreePath currentPath;

    /**
     * This standard constructor initializes the attributes.
     * Redexes will contain all Treepaths in an arraylist.
     * currentpath is the Treepath for one redex.
     */
    public RedexFinderVisitor() {
        redexes = new ArrayList<>();
        currentPath = new TreePath();
    }

    /**
     * This is a standard getter, which returns all tree paths for all redexes
     * @return The list of all tree paths
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
     * This was rudolf. He will be missed.
     * The class is called to find redexes. If the class finds redexes it'll add them to the redex list
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

