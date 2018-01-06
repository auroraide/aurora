package aurora.shared.backend.strategies;

import aurora.shared.backend.TreePath;
import aurora.shared.backend.tree.Term;

/**
 * This is the main class of the package "strategies". Every other strategy has to extend this class.
 */
public abstract class ReductionStrategy {
    /**
     * A strategy evaluates terms and choses a redex which gets reduced by the beta reducer. The Strategies return a treepath which show the chosen redex.
     * @param t the term which gets evaluated.
     * @return the treepath to the chosen redex.
     */
    abstract public TreePath getRedex(Term t);
}
