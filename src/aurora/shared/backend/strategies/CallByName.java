package aurora.shared.backend.strategies;

import aurora.shared.backend.TreePath;
import aurora.shared.backend.tree.Term;

/**
 * This is the Call By Name Strategy. The Strategy reduces the leftmost redex, when not enclosed by an abstraction.
 * This will be made by a depth first search, that doesn't go below abstractions.
 */
public class CallByName extends ReductionStrategy {

    @Override
    public TreePath getRedex(Term t) {
        return null;
    }

}
