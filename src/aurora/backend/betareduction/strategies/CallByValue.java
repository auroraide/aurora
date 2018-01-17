package aurora.backend.betareduction.strategies;

import aurora.backend.RedexPath;
import aurora.backend.tree.Term;

/**
 * This is the Call By Value strategy.
 * It reduces an abstraction which has a value as its parameter.
 * A value is an abstraction or a free variable.
 */
public class CallByValue extends ReductionStrategy {

    @Override
    public RedexPath getRedex(Term t) {
        return null;
    }

}
