package aurora.backend.betareduction.strategies;

import aurora.backend.RedexPath;
import aurora.backend.tree.Term;

/**
 * The user chooses the redex in this strategy.
 * The user can choose from all possible redexes and clicks on one.
 * The strategy calculates the RedexPath to the chosen redex.
 */
public class UserStrategy extends ReductionStrategy {

    @Override
    public RedexPath getRedex(Term t) {
        return null;
    }

}
