package aurora.backend.simplifier;

import aurora.backend.library.Library;
import aurora.backend.tree.*;
import aurora.backend.tree.Function;

/**
 * Simplify a given Term into a Function that is defined in some Library.
 * <p>
 * Checks if a given Term is in a Library.
 */
public class LibraryTermSimplifier implements ResultSimplifier<Function> {

    private Library library;

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
        return null;
    }

}
