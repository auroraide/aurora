package aurora.shared.backend.strategies;

import aurora.shared.backend.TreePath;
import aurora.shared.backend.tree.Term;

public abstract class ReductionStrategy {

    abstract public TreePath getRedex(Term t);
}
