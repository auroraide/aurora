package aurora.backend.betareduction;

import aurora.backend.RedexPath;
import aurora.backend.betareduction.strategies.ReductionStrategy;
import aurora.backend.betareduction.visitors.RedexFinderVisitor;
import aurora.backend.betareduction.visitors.ReplaceVisitor;
import aurora.backend.betareduction.visitors.SubstitutionVisitor;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.Term;

import java.util.List;

public class BetaReducer {

    private ReductionStrategy strategy;
    public boolean finished;

    /**
     * The constructor gets a strategy that is used for the reduction.
     *
     * @param strategy The chosen reduction strategy.
     */
    public BetaReducer(ReductionStrategy strategy) {
        this.strategy = strategy;
        finished = false;
    }

    /**
     * This method performs one beta reduction.
     *
     * @param term The Term that will get reduced.
     * @return null if not reducible, otherwise reduced Term.
     */
    public Term reduce(Term term) {
        finished = false;
        RedexPath path = strategy.getRedexPath(term);
        if (path == null) {
            finished = true;
            return term; // there is no reducible redex left, the given term is our result
        }
        Application app = path.get(term);
        SubstitutionVisitor substitutionVisitor = new SubstitutionVisitor(app.right);
        Term substituted = app.left.accept(substitutionVisitor);
        //seems hacky
        Abstraction subtitutedabs = (Abstraction) substituted;
        Term substitutedWithoutabs = subtitutedabs.body;
        ReplaceVisitor replaceVisitor = new ReplaceVisitor(path, substitutedWithoutabs);
        return term.accept(replaceVisitor);
    }


    // only for reference / example usage
    private Term reduceN(Term term, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        for (; n > 0; n--) {
            term = reduce(term);
            // Presenter will read term here and display it.
        }
        return term;
    }

    private List<RedexPath> findAllRedexes(Term term, RedexPath path) {
        RedexFinderVisitor redexFinderVisitor = new RedexFinderVisitor();
        term.accept(redexFinderVisitor);
        return redexFinderVisitor.getResult();
    }

}
