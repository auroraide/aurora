package aurora.backend.betareduction.strategies;

import aurora.backend.RedexPath;
import aurora.backend.tree.Term;

/**
 * This is the Call By Name Strategy. The Strategy reduces the leftmost redex, when not enclosed by an abstraction.
 * This will be made by a depth first search, that doesn't go below abstractions.
 */
public class CallByName extends ReductionStrategy {

    @Override
    public RedexPath getRedex(Term t) {
        return null;
    }

}
