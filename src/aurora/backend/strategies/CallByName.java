package aurora.backend.strategies;

import aurora.backend.TreePath;
import aurora.backend.tree.Term;

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
