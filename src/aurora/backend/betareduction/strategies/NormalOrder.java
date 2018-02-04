package aurora.backend.betareduction.strategies;

import aurora.backend.RedexPath;
import aurora.backend.TermVisitor;
import aurora.backend.tree.*;
import aurora.backend.tree.Function;

/**
 * The Normalorder is the default reduction strategy, it choses the leftmost redex.
 */
public class NormalOrder extends ReductionStrategy {

    @Override
    public RedexPath getRedex(Term t) {

        FirstRedexFinderVisitor redexfinder = new FirstRedexFinderVisitor();
        t.accept(redexfinder);
        if (redexfinder.foundredex) {
            return redexfinder.path;

        } else {
            return null;
        }
    }


    private class FirstRedexFinderVisitor extends TermVisitor<Void> {

        private RedexPath path;
        private boolean foundredex = false;

        public FirstRedexFinderVisitor() {
            path = new RedexPath();
        }

        public RedexPath getPath() {
            return path;
        }

        @Override
        public Void visit(Abstraction abs) {
            return abs.body.accept(this);
        }

        @Override
        public Void visit(Application app) {
            app.left.accept(new AbstractionFinder());
            while (!foundredex) {
                path.push(RedexPath.Direction.LEFT);
                app.left.accept(this);
                if (foundredex) {
                    return null;
                }
                path.pop();
                path.push(RedexPath.Direction.RIGHT);
                app.right.accept(this);
                if (foundredex) {
                    return null;
                }
                path.pop();
            }
            return null;
        }

        @Override
        public Void visit(BoundVariable bvar) {
            return null;
        }

        @Override
        public Void visit(FreeVariable fvar) {
            return null;
        }

        @Override
        public Void visit(Function libterm) {
            return null;
        }

        @Override
        public Void visit(ChurchNumber c) {
            Abstraction abs = c.getAbstraction();
            abs.body.accept(this);
            return null;
        }


        /**
         * Visitor that helps find abstractions inside our Term tree.
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
                return null;
            }

            @Override
            public Void visit(FreeVariable fvar) {
                return null;
            }

            @Override
            public Void visit(Function libterm) {
                return null;
            }

            @Override
            public Void visit(ChurchNumber c) {
                foundredex =  true;
                return null;
            }

        }
    }
}
