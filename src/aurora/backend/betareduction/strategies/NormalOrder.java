package aurora.backend.betareduction.strategies;

import aurora.backend.RedexPath;
import aurora.backend.tree.Term;

/**
 * The Normalorder is the default reduction strategy, it choses the leftmost redex.
 */
public class NormalOrder extends ReductionStrategy {

    @Override
    public RedexPath getRedex(Term t) {
        return null;
    }

}
