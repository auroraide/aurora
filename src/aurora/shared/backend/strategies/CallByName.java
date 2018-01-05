package aurora.shared.backend.strategies;

import aurora.shared.backend.TreePath;
import aurora.shared.backend.tree.Term;

/**
 * this is the Call By Name Strategy. The Strategy reduces the leftmost redex if it isn't enclose by an abstraction. This will be made by a depth first search, that doesnt go below abstractions.
 */
public class CallByName extends ReductionStrategy {
    @Override
    public TreePath getRedex(Term t) {
        return null;
    }
}
