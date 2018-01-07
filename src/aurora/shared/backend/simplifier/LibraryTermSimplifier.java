package aurora.shared.backend.simplifier;

import aurora.shared.backend.library.Library;
import aurora.shared.backend.tree.LibraryTerm;
import aurora.shared.backend.tree.Term;

/**
 * Simplify a given Term into a LibraryTerm that is defined in some Library.
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
