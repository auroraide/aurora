package aurora.shared.backend.strategies;

import aurora.shared.backend.TreePath;
import aurora.shared.backend.tree.Term;

/**
 * The User choses the Redex in this strategy.
 * The user can chose from all possible redexes and clicks on one.
 * The strategy calculates the Treepath to the chosen redex.
 */
public class UserStrategy extends ReductionStrategy {
    @Override
    public TreePath getRedex(Term t) {
        return null;
    }
}
