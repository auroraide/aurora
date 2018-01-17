package aurora.backend.betareduction.strategies;

import aurora.backend.RedexPath;
import aurora.backend.betareduction.BetaReducer;
import aurora.backend.tree.Term;

/**
 * A beta reduction strategy.
 *
 * <p>A strategy evaluates terms and choses a redex to be reduced by the {@link BetaReducer}.
 * The location within the Term tree is given via a {@link RedexPath} object.
 */
public abstract class ReductionStrategy {

    /**
     * A strategy evaluates terms and choses a redex which gets reduced by the {@link BetaReducer}.
     * The Strategies return a treepath which show the chosen redex.
     *
     * @param t the term which gets evaluated.
     * @return the tree path to the chosen redex.
     */
    public abstract RedexPath getRedex(Term t);

}
