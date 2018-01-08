package aurora.backend.strategies;

import aurora.backend.TreePath;
import aurora.backend.tree.Term;

/**
 * The Normalorder is the default reduction strategy, it choses the leftmost redex.
 */
public class NormalOrder extends ReductionStrategy {

    @Override
    public TreePath getRedex(Term t) {
        return null;
    }

}
