package aurora.backend.betareduction.strategies;

import aurora.backend.RedexPath;
import aurora.backend.tree.Term;

/**
 * The user chooses the redex in this strategy.
 * The user can choose from all possible redexes and clicks on one.
 * The strategy calculates the RedexPath to the chosen redex.
 */
public class UserStrategy extends ReductionStrategy {
    private RedexPath path;
    private boolean valid;

    public UserStrategy(RedexPath path) {
        this.path = path;
        valid = true;
    }

    public void setRedexPath(RedexPath path) {
        this.path = path;
        valid = true;
    }

    @Override
    public RedexPath getRedexPath(Term t) {
        valid = false;
        return path;
    }

}
