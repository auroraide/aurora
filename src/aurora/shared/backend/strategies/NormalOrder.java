package aurora.shared.backend.strategies;

import aurora.shared.backend.TreePath;
import aurora.shared.backend.tree.Term;

/**
 * The Normalorder is the default reduction strategy, it choses the leftmost redex.
 */
public class NormalOrder extends ReductionStrategy {
    @Override
    public TreePath getRedex(Term t) {
        return null;
    }
}
