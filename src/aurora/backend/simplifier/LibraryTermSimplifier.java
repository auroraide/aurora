package aurora.backend.simplifier;

import aurora.backend.library.Library;
import aurora.backend.tree.LibraryTerm;
import aurora.backend.tree.Term;

/**
 * Simplify a given Term into a LibraryTerm that is defined in some Library.
 *
 * Checks if a given Term is in a Library.
 */
public class LibraryTermSimplifier implements ResultSimplifier<LibraryTerm> {

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
    public LibraryTerm simplify(Term t) {
        return null;
    }

}
