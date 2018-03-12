package aurora.backend.simplifier;

import aurora.backend.SimplifierVisitor;
import aurora.backend.library.Library;
import aurora.backend.library.LibraryItem;
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

}
