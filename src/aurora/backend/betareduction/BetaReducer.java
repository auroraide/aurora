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

public class BetaReducer {

    private ReductionStrategy strategy;
    private boolean finished;
    private boolean alwaystrue;

    /**
     * The constructor gets a strategy that is used for the reduction.
     *
     * @param strategy The chosen reduction strategy.
     */
    public BetaReducer(ReductionStrategy strategy) {
        this.strategy = strategy;
        finished = false;
        alwaystrue = false;
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

        // there is no reducible redex left, the given term is our result
        if (path == null) {
            finished = true;
            return term;
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

    public boolean getFinished() {
        return finished;
    }

    private class CastingVisitor extends TermVisitor<Abstraction> {

        @Override
        public Abstraction visit(Abstraction abs) {
            alwaystrue = true;
            return abs;
        }

        @Override
        public Abstraction visit(Application app) {
            alwaystrue = false;
            return null;
        }

        @Override
        public Abstraction visit(BoundVariable bvar) {
            alwaystrue = false;
            return null;
        }

        @Override
        public Abstraction visit(FreeVariable fvar) {
            alwaystrue = false;
            return null;
        }

        @Override
        public Abstraction visit(Function function) {
            Term t = function.term;
            t.accept(this);
            return null;
        }

        @Override
        public Abstraction visit(ChurchNumber c) {
            alwaystrue = true;
            return c.getAbstraction();
        }
    }
}
