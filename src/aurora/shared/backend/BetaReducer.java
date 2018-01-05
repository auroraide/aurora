package aurora.shared.backend;

import aurora.shared.backend.strategies.ReductionStrategy;
import aurora.shared.backend.tree.Application;
import aurora.shared.backend.tree.Term;

public class BetaReducer {
    /**
     *
     * @param term
     * @param strategy
     * @return null if not reducible, otherwise reduced Term.
     */
    public static Term reduce(Term term, ReductionStrategy strategy) {
        TreePath path = strategy.getRedex(term);
        if (path == null) return null;
        Application app = path.get(term);
        SubstitutionVisitor substitutionVisitor = new SubstitutionVisitor(app.getRight());
        Term substituted = app.getLeft().accept(substitutionVisitor);

        ReplaceVisitor replaceVisitor = new ReplaceVisitor(path.iterator(), substituted);
        return term.accept(replaceVisitor);
    }

    public static Term reduceN(Term term, ReductionStrategy strategy, int n) {
        if (n <= 0) throw new IllegalArgumentException();
        for (; n > 0; n--) {
            term = reduce(term, strategy);
            // Presenter will read term here and display it.
        }
        return term;
    }

}
