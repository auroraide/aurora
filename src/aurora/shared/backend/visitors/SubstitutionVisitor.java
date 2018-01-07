package aurora.shared.backend.visitors;

import aurora.shared.backend.tree.*;

/**
 * Visitor that traverses the Term tree and substitutes a BoundVariable with a given Term.
 */
public class SubstitutionVisitor implements TermVisitor<Term> {

    private final int index;

    private final Term with;

    /**
     * This constructor gets a term. The index will automatically be 0.
     *
     * @param with a term
     */
    public SubstitutionVisitor(Term with) {
        this(0, with);
    }

    /**
     * This constructor gets a term and an index. It fills the attributes with these values.
     *
     * @param index The index of the visitor.
     * @param with The term that will substitute something.
     */
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

    /**
     * De Bruijn indices have to be fixed if you execute a substitution.
     */
    private class DebruijnFixVisitor implements TermVisitor<Term> {

        private final int innerIndex;

        /**
         * This gets an index and saves it as innerIndex.
         *
         * @param index the index of the abstraction.
         */
        private DebruijnFixVisitor(int index) {
            this.innerIndex = index;
        }

        @Override
        public Term visit(Abstraction abs) {
            return new Abstraction(abs.getBody().accept(new DebruijnFixVisitor(innerIndex + 1)), abs.getName());
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
            if (bvar.getIndex() > innerIndex) {
                return new BoundVariable(bvar.getIndex() + index - innerIndex + 1);
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
