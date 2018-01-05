package aurora.shared.backend;


import aurora.shared.backend.tree.*;

import java.util.ArrayList;
import java.util.List;

public class RedexFinderVisitor implements TermVisitor<Void> {
    private List<TreePath> redexes;

    public RedexFinderVisitor() {
        this.redexes = new ArrayList<>();
    }

    public List<TreePath> getResult() {
        return redexes;
    }

    @Override
    public Void visit(Abstraction abs) {
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
        return null;
    }
}
