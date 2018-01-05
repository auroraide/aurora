package aurora.shared.backend.visitors;

import aurora.shared.backend.tree.*;

/**
 *
 */
public class SubstitutionVisitor implements TermVisitor<Term> {

    private final int index;

    private final Term with;

    /**
	 *
	 */
    public SubstitutionVisitor(Term with) {
        this(0, with);
    }
    
    private SubstitutionVisitor(int index, Term with) {
        this.index = index;
        this.with = with;
    }

    @Override
    public Term visit(Abstraction abs) {
        return new Abstraction(abs.getBody().accept(new SubstitutionVisitor(index + 1, with)), abs.getName());
    }

    @Override
    public Term visit(Application app) {
        return new Application(
            app.getLeft().accept(this),
            app.getRight().accept(this)
        );
    }

    @Override
    public Term visit(BoundVariable bvar) {
        if (bvar.getIndex() == this.index) {
            return with.accept(new DebruijnFixVisitor(0));
        }
        return bvar;
    }

    @Override
    public Term visit(FreeVariable fvar) {
        return fvar;
    }

    @Override
    public Term visit(LibraryTerm libterm) {
        return libterm;
    }

    @Override
    public Term visit(ChurchNumber c) {
        return c;
    }

    @Override
    public Term visit(Parenthesis p) {
        return p;
    }

    private class DebruijnFixVisitor implements TermVisitor<Term> {

        private final int innerindex;

        private DebruijnFixVisitor(int index) {
            this.innerindex = index;
        }

        @Override
        public Term visit(Abstraction abs) {
            return new Abstraction(abs.getBody().accept(new DebruijnFixVisitor(innerindex + 1)), abs.getName());
        }

        @Override
        public Term visit(Application app) {
            return new Application(
                app.getLeft().accept(this),
                app.getRight().accept(this)
            );
        }

        @Override
        public Term visit(BoundVariable bvar) {
            if (bvar.getIndex() > innerindex) {
                return new BoundVariable(bvar.getIndex() + index - innerindex + 1);
            }
            return bvar;
        }

        @Override
        public Term visit(FreeVariable fvar) {
            return fvar;
        }

        @Override
        public Term visit(LibraryTerm libterm) {
            return libterm;
        }

        @Override
        public Term visit(ChurchNumber c) {
            return c;
        }

        @Override
        public Term visit(Parenthesis p) { return p; }

    }

}
