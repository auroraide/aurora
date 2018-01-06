package aurora.shared.backend.visitors;

import aurora.shared.backend.tree.*;

/**
 * This class implements the Term visitor, it is the "Concrete Visitor" in the visitor pattern.
 * This visitor traverses the tree and substitutes a bounded variable with a term.
 */
public class SubstitutionVisitor implements TermVisitor<Term> {

    private final int index;

    private final Term with;

    /**
     * This constructor gets a term. The index will automatically be 0.
     * @param with a term
     */
    public SubstitutionVisitor(Term with) {
        this(0, with);
    }

    /**
     * This constructor gets a term and an index. It fills the attributes with these values.
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
     * This is a nested class that fixes the values of the bounded variables. You have to fix De-Bruijn indices if you substitute something
     */
    public class DebruijnFixVisitor implements TermVisitor<Term> {

        private final int innerindex;

        /**
         * This gets an index and saves it as innerindex
         * @param index the index of the abstraction
         */
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
