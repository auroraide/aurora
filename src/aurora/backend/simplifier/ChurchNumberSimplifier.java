package aurora.backend.simplifier;

import aurora.backend.TermVisitor;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;

/**
 * Checks if a given Term is a Church number and if this is the case, returns the Church number.
 */
public class ChurchNumberSimplifier implements ResultSimplifier<ChurchNumber> {

    @Override
    public ChurchNumber simplify(Term t) {
        int counter = 0;
        Abstraction firstabs = t.accept(new Absfinder());
        if (firstabs != null) {
            Abstraction secondabs = firstabs.body.accept(new Absfinder());
            if (secondabs != null) {
                Term apporbvar = secondabs.body;
                while (apporbvar.accept(new Appfinder()) != null) {
                    counter++;
                    Application app = (Application) apporbvar;
                    if (app.left.accept(new BVarfinder(2)) == null) {
                        return null;
                    }
                    if (app.right.accept(new BVarfinder(1)) != null) {
                        return new ChurchNumber(counter);
                    }
                    apporbvar = app.right;
                }
                if (apporbvar.accept(new BVarfinder(1)) != null) {
                    return new ChurchNumber(counter);

                } else {
                    return null;
                }
            }


        } else {
            return null;
        }
        return null;
    }

    private class Absfinder extends TermVisitor<Abstraction> {

        @Override
        public Abstraction visit(Abstraction abs) {
            return abs;
        }

        @Override
        public Abstraction visit(Application app) {
            return null;
        }

        @Override
        public Abstraction visit(BoundVariable bvar) {
            return null;
        }

        @Override
        public Abstraction visit(FreeVariable fvar) {
            return null;
        }

        @Override
        public Abstraction visit(Function libterm) {
            return libterm.term.accept(this);
        }

        @Override
        public Abstraction visit(ChurchNumber c) {
            return c.getAbstraction();
        }
    }

    private class Appfinder extends TermVisitor<Application> {

        @Override
        public Application visit(Abstraction abs) {
            return null;
        }

        @Override
        public Application visit(Application app) {
            return app;
        }

        @Override
        public Application visit(BoundVariable bvar) {
            return null;
        }

        @Override
        public Application visit(FreeVariable fvar) {
            return null;
        }

        @Override
        public Application visit(Function libterm) {
            return libterm.term.accept(this);
        }

        @Override
        public Application visit(ChurchNumber c) {
            return null;
        }
    }

    private class BVarfinder extends TermVisitor<BoundVariable> {
        int index;

        BVarfinder(int i) {
            this.index = i;
        }

        @Override
        public BoundVariable visit(Abstraction abs) {
            return null;
        }

        @Override
        public BoundVariable visit(Application app) {
            return null;
        }

        @Override
        public BoundVariable visit(BoundVariable bvar) {
            if (bvar.index == index) {
                return bvar;
            } else {
                return null;
            }

        }

        @Override
        public BoundVariable visit(FreeVariable fvar) {
            return null;
        }

        @Override
        public BoundVariable visit(Function libterm) {
            return libterm.term.accept(this);
        }

        @Override
        public BoundVariable visit(ChurchNumber c) {
            return null;
        }
    }
}
