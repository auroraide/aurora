package aurora.shared.backend.strategies;

import aurora.shared.backend.TreePath;
import aurora.shared.backend.tree.Term;

/**
 * This is the Call By Value strategy. It reduces an abstraction which has a "value" as it's parameter. A value is an abstraction or a free variable.
 */
public class CallByValue extends ReductionStrategy {

    @Override
    public TreePath getRedex(Term t) {
        return null;
    }

}
