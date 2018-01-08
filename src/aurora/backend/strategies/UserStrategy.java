package aurora.backend.strategies;

import aurora.backend.TreePath;
import aurora.backend.tree.Term;

/**
 * The user chooses the redex in this strategy.
 * The user can choose from all possible redexes and clicks on one.
 * The strategy calculates the TreePath to the chosen redex.
 */
public class UserStrategy extends ReductionStrategy {

    @Override
    public TreePath getRedex(Term t) {
        return null;
    }

}
