package aurora.shared.backend.simplifier;

import aurora.shared.backend.tree.Term;

/**
 * Simplify a given result Term into some predefined Term.
 * This can be helpful to get back to a more compact form (e.g., a number).
 *
 * @param <T> The generic return type of the {@link #guess(Term) guess} method.
 */
public interface ResultSimplifier<T extends Term> {

    /**
     * Simplify a Term.
     *
     * @param t The term that you wish to simplify.
     * @return The simplified Term if possible or null.
     */
    T simplify(Term t);

}
