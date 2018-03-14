package aurora.backend.betareduction.visitors;

import aurora.backend.RedexPath;
import aurora.backend.RedexPath.Direction;
import aurora.backend.TermVisitor;
import aurora.backend.betareduction.Replacer;
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

    Replacer replacer;

    public ReplaceVisitor(RedexPath path, Term with) {
        this.replacer = new Replacer(path, with);
    }

    @Override
    public Term visit(Abstraction abs) {
        return this.replacer.replace(abs);
    }

    @Override
    public Term visit(Application app) {
        return this.replacer.replace(app);
    }

    @Override
    public Term visit(BoundVariable bvar) {
        assert false;
        return null;
    }

    @Override
    public Term visit(FreeVariable fvar) {
        assert false;
        return null;
    }

    @Override
    public Term visit(Function function) {
        return this.replacer.replace(function);
    }

    @Override
    public Term visit(ChurchNumber c) {
        return this.replacer.replace(c);
    }




}
