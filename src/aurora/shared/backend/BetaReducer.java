package aurora.shared.backend;

import aurora.shared.backend.strategies.ReductionStrategy;
import aurora.shared.backend.tree.Term;

public class BetaReducer {
    ReductionStrategy strategy;
    Term t;
    public BetaReducer(ReductionStrategy strategy, Term t) {
        this.strategy = strategy;
        this.t = t;
    }


}
