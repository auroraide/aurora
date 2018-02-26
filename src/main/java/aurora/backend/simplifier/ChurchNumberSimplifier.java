package aurora.backend.simplifier;

import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.Term;

/**
 * Checks if a given Term is a Church number and if this is the case, returns the Church number.
 */
public class ChurchNumberSimplifier implements ResultSimplifier<ChurchNumber> {

    @Override
    public ChurchNumber simplify(Term t) {
        return null;
    }

}
