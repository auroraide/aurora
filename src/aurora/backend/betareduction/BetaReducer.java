package aurora.backend.betareduction;

import aurora.backend.RedexPath;
import aurora.backend.TermVisitor;
import aurora.backend.betareduction.strategies.ReductionStrategy;
import aurora.backend.betareduction.visitors.RedexFinderVisitor;
import aurora.backend.betareduction.visitors.ReplaceVisitor;
import aurora.backend.betareduction.visitors.SubstitutionVisitor;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;

import java.util.List;

/**
 * Performs beta reduction on {@link Term} trees.
 */
public class BetaReducer {

    private ReductionStrategy strategy;
    private boolean alwaystrue;

    /**
     * The constructor gets a strategy that is used for the reduction.
     *
     * @param strategy The chosen reduction strategy.
     */
    public BetaReducer(ReductionStrategy strategy) {
        this.strategy = strategy;
        this.alwaystrue = false;
    }

    /**
     * This method performs a single beta reduction step.
     *
     * @param term The {@link Term} that will get reduced.
     * @return The reduced {@link Term} or null if no further beta reduction was possible.
     */
    public Term reduce(Term term) {
        RedexPath path = strategy.getRedexPath(term);

        // there is no reducible redex left, the given term is our result
        if (path == null) {
            return null;
        }

        Application app = path.get(term);
        SubstitutionVisitor substitutionVisitor = new SubstitutionVisitor(app.right);
        Term substituted = app.left.accept(substitutionVisitor);
        Abstraction substitutedabs = substituted.accept(new CastingVisitor());
        if (substitutedabs == null) {
            assert false : "The Redexpath was wrong, there is no redex here. This should never happen";
        }
        Term substitutedWithoutabs = substitutedabs.body;
        ReplaceVisitor replaceVisitor = new ReplaceVisitor(path, substitutedWithoutabs);
        return term.accept(replaceVisitor);
    }


    private class CastingVisitor extends TermVisitor<Abstraction> {

        @Override
        public Abstraction visit(Abstraction abs) {
            return abs;
        }

        @Override
        public Abstraction visit(Application app) {
            assert false : "This can't happen";
            return null;
        }

        @Override
        public Abstraction visit(BoundVariable bvar) {
            assert false : "This can't happen";
            return null;
        }

        @Override
        public Abstraction visit(FreeVariable fvar) {
            assert false : "This can't happen";
            return null;
        }

        @Override
        public Abstraction visit(Function function) {
            assert false : "the function got changed it isn't a function anymore this can'T happen";
            Term t = function.term;
            t.accept(this);
            return null;
        }

        @Override
        public Abstraction visit(ChurchNumber c) {
            assert false : "the churchnumber got changed it isn't a Churchnumber anymore";
            return null;
        }
    }
}
