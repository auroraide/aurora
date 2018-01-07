package aurora.shared.backend.guesser;

import aurora.shared.backend.tree.LibraryTerm;
import aurora.shared.backend.tree.Term;

/**
 * This class analyzes a Term and looks it up in the Libraries. If it finds a library term that is identical to the given Term it returns the library term.
 */
public class LibraryGuesser implements ResultGuesser<LibraryTerm> {
    @Override
    public LibraryTerm guess(Term t) {
        return null;
    }
}
