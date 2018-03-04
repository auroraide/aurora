package aurora.backend.simplifier;

import aurora.backend.TermVisitor;
import aurora.backend.library.Library;
import aurora.backend.library.LibraryItem;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;

/**
 * Simplify a given Term into a Function that is defined in some Library.
 */
public class LibraryTermSimplifier implements ResultSimplifier<Function> {

    private final Library library;

    /**
     * Constructor that takes a Library.
     *
     * @param library Library instance used for the lookup.
     */
    public LibraryTermSimplifier(Library library) {
        this.library = library;
    }

    @Override
    public Function simplify(Term t) {
        for (LibraryItem l : this.library) {
            // TODO interlocking visitor that yields after each step to be able to skip as soon as the terms diverge
            if (l.getTerm().accept(new SimplifierVisitor()).equals(t.accept(new SimplifierVisitor()))) {

                return new Function(l.getName(), l.getTerm());

            }
        }

        return null;
    }

    private class SimplifierVisitor extends TermVisitor<String> {

        @Override
        public String visit(Abstraction abs) {
            return "(\\." + abs.body.accept(this) + ")";
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
            return "*";
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

}
