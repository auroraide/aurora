package aurora.backend.betareduction;

import aurora.backend.RedexPath;
import aurora.backend.TermVisitor;
import aurora.backend.betareduction.strategies.ReductionStrategy;
import aurora.backend.betareduction.visitors.ReplaceVisitor;
import aurora.backend.betareduction.visitors.SubstitutionVisitor;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;

public class BetaReducer {

    private ReductionStrategy strategy;
    private RedexPath lastPath;

    /**
     * The constructor gets a strategy that is used for the reduction.
     *
     * @param strategy The chosen reduction strategy.
     */
    public BetaReducer(ReductionStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * This method performs one beta reduction.
     *
     * @param term The Term that will get reduced.
     * @return something.
     */
    public Term reduce(Term term) {
        RedexPath path = strategy.getRedexPath(term);
        lastPath = path;

        // there is no reducible redex left, the given term is our result
        if (path == null) {
            return null;
        }
        Application app = path.get(term);
        SubstitutionVisitor substitutionVisitor = new SubstitutionVisitor(app.right);
        Term substituted = app.left.accept(substitutionVisitor);
        Abstraction substitutedabs = substituted.accept(new CastingVisitor());
        Term substitutedWithoutabs = substitutedabs.body;
        ReplaceVisitor replaceVisitor = new ReplaceVisitor(path, substitutedWithoutabs);
        return term.accept(replaceVisitor);
    }

    public RedexPath getLastPath() {
        return lastPath;
    }

    private class CastingVisitor extends TermVisitor<Abstraction> {

        @Override
        public Abstraction visit(Abstraction abs) {
            return abs;
        }

        @Override
        public Abstraction visit(Application app) {
            //An abstraction isn an application
            throw new RuntimeException();
        }

        @Override
        public Abstraction visit(BoundVariable bvar) {
            //An abstraction isnt a bvar
            throw new RuntimeException();

        }

        @Override
        public Abstraction visit(FreeVariable fvar) {
            // An abstraction is a fvar.
            throw new RuntimeException();
        }

        @Override
        public Abstraction visit(Function function) {
            // the function got changed it isn't a function anymore this can't happen and it can't be tested;
            throw new RuntimeException();

        }

        @Override
        public Abstraction visit(ChurchNumber c) {
            // the churchnumber got changed it isn't a Churchnumber anymore, this cant happen and can't be tested
            throw new RuntimeException();
        }
    }
}
