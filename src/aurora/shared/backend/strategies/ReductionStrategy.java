package aurora.shared.backend;

public abstract class ReductionStrategy {

    abstract public TreePath getRedex(Term t);
}
