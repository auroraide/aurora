package aurora.shared.backend.visitors;


import aurora.shared.backend.TreePath;
import aurora.shared.backend.tree.*;

import java.util.ArrayList;
import java.util.List;

public class RedexFinderVisitor implements TermVisitor<Void> {
    private List<TreePath> redexes;
    private TreePath currentPath;

    public RedexFinderVisitor() {
        redexes = new ArrayList<>();
        currentPath = new TreePath();
    }

    public List<TreePath> getResult() {
        return redexes;
    }

    @Override
    public Void visit(Abstraction abs) {
        return abs.getBody().accept(this);
    }

    @Override
    public Void visit(Application app) {
        app.getLeft().accept(new Rudolf());

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

    private class Rudolf implements TermVisitor<Void> {
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

