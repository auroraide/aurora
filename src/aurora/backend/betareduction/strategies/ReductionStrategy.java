package aurora.backend.betareduction.strategies;

import aurora.backend.TreePath;
import aurora.backend.tree.Term;

/**
 * This is the main class of the package "strategies". Every other strategy has to extend this class.
 */
public abstract class ReductionStrategy {

    /**
     * A strategy evaluates terms and choses a redex which gets reduced by the beta reducer. The Strategies return a treepath which show the chosen redex.
     * @param t the term which gets evaluated.
     * @return the tree path to the chosen redex.
     */
    abstract public TreePath getRedex(Term t);

}
