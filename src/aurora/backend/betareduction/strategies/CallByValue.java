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
 * This is the Call By Value strategy.
 * It reduces an abstraction which has a value as its parameter.
 * A value is an abstraction or a free variable.
 */
public class CallByValue extends ReductionStrategy {

    @Override
    public RedexPath getRedex(Term t) {

        FirstRedexFinderVisitor redexfinder = new FirstRedexFinderVisitor();
        t.accept(redexfinder);
        if (redexfinder.foundredex && redexfinder.foundvalue) {
            return redexfinder.path;

        } else {
            return null;
        }
    }


    private class FirstRedexFinderVisitor extends TermVisitor<Void> {

        private RedexPath path;
        private boolean foundredex = false;
        private boolean foundvalue = false;

        public FirstRedexFinderVisitor() {
            path = new RedexPath();
        }

        public RedexPath getPath() {
            return path;
        }

        @Override
        public Void visit(Abstraction abs) {
            return null;
        }

        @Override
        public Void visit(Application app) {
            app.left.accept(new AbstractionFinder());
            app.right.accept(new ValueFinder());
            if (foundredex ^ foundvalue) {
                foundvalue = false;
                foundredex = false;
            }
            while (!foundredex && !foundvalue) {
                path.push(RedexPath.Direction.LEFT);
                app.left.accept(this);
                if (foundredex ^ foundvalue) {
                    foundvalue = false;
                    foundredex = false;
                }
                if (foundredex && foundvalue) {
                    return null;
                }
                path.pop();
                path.push(RedexPath.Direction.RIGHT);
                app.right.accept(this);
                if (foundredex ^ foundvalue) {
                    foundvalue = false;
                    foundredex = false;
                }
                if (foundredex && foundvalue) {
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
                return null;
            }

        }

        private class ValueFinder extends TermVisitor<Void> {

            @Override
            public Void visit(Abstraction abs) {
                foundvalue = true;
                return null;
            }

            @Override
            public Void visit(Application app) {
                return null;
            }

            @Override
            public Void visit(BoundVariable bvar) {
                foundvalue = true;
                return null;
            }

            @Override
            public Void visit(FreeVariable fvar) {
                foundvalue = true;
                return null;
            }

            @Override
            public Void visit(Function libterm) {
                return null;
            }

            @Override
            public Void visit(ChurchNumber c) {
                foundvalue = true;
                return null;
            }
        }
    }

}
