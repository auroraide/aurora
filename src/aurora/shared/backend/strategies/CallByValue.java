package aurora.shared.backend.strategies;

import aurora.shared.backend.TreePath;
import aurora.shared.backend.tree.Term;

public class CallByValue extends ReductionStrategy {
    @Override
    public TreePath getRedex(Term t) {
        return null;
    }
}