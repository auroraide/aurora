package aurora.backend.strategies;

import aurora.backend.TreePath;
import aurora.backend.tree.Term;

/**
 * This is the Call By Value strategy. It reduces an abstraction which has a "value" as it's parameter. A value is an abstraction or a free variable.
 */
public class CallByValue extends ReductionStrategy {

    @Override
    public TreePath getRedex(Term t) {
        return null;
    }

}
