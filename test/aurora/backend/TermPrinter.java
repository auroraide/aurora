package aurora.backend;

import aurora.backend.TermVisitor;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;

public class TermPrinter extends TermVisitor<String> {

    @Override
    public String visit(Abstraction abs) {
        return "(\\" + abs.name + "." + abs.body.accept(this) + ")";
    }

    @Override
    public String visit(Application app) {
        return "(" + app.left.accept(this) + " " + app.right.accept(this) + ")";
    }

    @Override
    public String visit(BoundVariable bvar) {
        return "" + bvar.index;
    }

    @Override
    public String visit(FreeVariable fvar) {
        return fvar.name;
    }

    @Override
    public String visit(Function libterm) {
        return "$" + libterm.name;
    }

    @Override
    public String visit(ChurchNumber c) {
        return "c" + c.value;
    }

}
