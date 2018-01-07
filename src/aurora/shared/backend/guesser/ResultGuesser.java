package aurora.shared.backend.guesser;

import aurora.shared.backend.tree.Term;

/**
 * This interface is used to analyze a Term and it returns a more readable result.
 * @param <T> Return Type of the guess methods.
 */
public interface ResultGuesser<T extends Term> {
    /**
     * This method gets a Term and tries to find a replacement for the term.
     * @param t The given Term.
     * @return The replacement of the Term.
     */
    T guess(Term t);
}
