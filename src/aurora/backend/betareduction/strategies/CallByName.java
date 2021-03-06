package aurora.backend.betareduction.strategies;

import aurora.backend.RedexPath;
import aurora.backend.TermVisitor;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;

/**
 * This is the Call By Name Strategy. The Strategy reduces the leftmost redex, when not enclosed by an abstraction.
 * This will be made by a depth first search, that doesn't go below abstractions.
 */
public class CallByName extends ReductionStrategy {

    @Override
    public RedexPath getRedexPath(Term t) {

        FirstRedexFinderVisitor redexfinder = new FirstRedexFinderVisitor();
        t.accept(redexfinder);
        if (redexfinder.foundredex) {
            return redexfinder.path;

        } else {
            return null;
        }
    }

    /**
     * traverses the tree and seeks out for redexes.
     */
    private class FirstRedexFinderVisitor extends TermVisitor<Void> {
        private RedexPath path;
        private boolean foundredex = false;

        public FirstRedexFinderVisitor() {
            path = new RedexPath();
        }

        @Override
        public Void visit(Abstraction abs) {
            return null;
        }

        @Override
        public Void visit(Application app) {
            app.left.accept(new AbstractionFinder());
            if (foundredex) { // found a redex
                return null;
            }

            path.push(RedexPath.Direction.LEFT);
            app.left.accept(this);
            if (foundredex) { // found a redex
                return null;
            }
            path.pop(); // there was no redex in this subtree
            path.push(RedexPath.Direction.RIGHT);
            app.right.accept(this);
            if (foundredex) {
                return null;
            }
            path.pop();

            return null;
        }

        @Override
        public Void visit(BoundVariable bvar) {
            // "found Bound Variable outside Abstraction, check for Parser/Reducer-errors";
            throw new RuntimeException();

        }

        @Override
        public Void visit(FreeVariable fvar) {
            return null;
        }

        @Override
        public Void visit(Function function) {
            Term t = function.term;
            t.accept(this);
            return null;
        }

        @Override
        public Void visit(ChurchNumber c) {
            return null;
        }

        /**
         * is this element an abstraction.?
         */
        private class AbstractionFinder extends TermVisitor<Void> {

            @Override
            public Void visit(Abstraction abs) {
                foundredex = true;
                return null;
            }

            @Override
            public Void visit(Application app) {
                return null;
            }

            @Override
            public Void visit(BoundVariable bvar) {
                // "found a Bound Variable that isn't in an abstraction search for parser/reducer errors";
                throw new RuntimeException();
            }

            @Override
            public Void visit(FreeVariable fvar) {
                return null;
            }

            @Override
            public Void visit(Function function) {
                Term t = function.term;
                t.accept(this);
                return null;
            }

            @Override
            public Void visit(ChurchNumber c) {
                foundredex = true;
                return null;
            }

        }
    }

}
