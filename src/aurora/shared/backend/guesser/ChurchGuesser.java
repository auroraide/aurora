package aurora.shared.backend.guesser;

import aurora.shared.backend.tree.ChurchNumber;
import aurora.shared.backend.tree.Term;

/**
 * This class analyzes a Term and if the given Term is a Church Number it returns the Church number.
 */
public class ChurchGuesser implements ResultGuesser<ChurchNumber> {
    @Override
    public ChurchNumber guess(Term t) {
        return null;
    }
}
