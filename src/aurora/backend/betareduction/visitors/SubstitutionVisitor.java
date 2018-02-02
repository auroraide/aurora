package aurora.backend.betareduction.visitors;

import aurora.backend.TermVisitor;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.LibraryTerm;
import aurora.backend.tree.Term;

/**
 * Visitor that traverses the Term tree and substitutes a BoundVariable with a given Term.
 */
public class SubstitutionVisitor extends TermVisitor<Term> {

    private final int index;

    private final Term with;

    /**
     * This constructor gets a term. The index will automatically be 0.
     *
     * @param with The term that will get substituted.
     */
    public SubstitutionVisitor(Term with) {
        this(0, with);
    }

    /**
     * This constructor gets a term and an index. It fills the attributes with these values.
     *
     * @param index The index of the visitor.
     * @param with  The term that will substitute something.
     */
    private SubstitutionVisitor(int index, Term with) {
        this.index = index;
        this.with = with;
    }

    @Override
    public Term visit(Abstraction abs) {
        return new Abstraction(abs.body.accept(new SubstitutionVisitor(index + 1, with)), abs.name);
    }

    @Override
    public Term visit(Application app) {
        int appindex = index;
        return new Application(
                app.left.accept(new SubstitutionVisitor(index,with)),
                app.right.accept(new SubstitutionVisitor(index,with))
        );
    }

    @Override
    public Term visit(BoundVariable bvar) {
        if (bvar.index == this.index) {
            //return with.accept(new DebruijnFixVisitor(0));
            return with.accept(new DebruijnFixWithVisitor(bvar.index,0));
        }
        if (bvar.index > this.index) {
            int updateindex = bvar.index - 1;
            return new BoundVariable(updateindex);
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


    /**
     * this class fixes the debruijnindixes in the with term that is given the substitution visitor.
     */
    private class DebruijnFixWithVisitor extends TermVisitor<Term> {
        int innercounter; //starts with 0
        int bvindex;

        public DebruijnFixWithVisitor(int index, int innercounter) {
            this.bvindex = index;
            this.innercounter = innercounter;

        }

        @Override
        public Term visit(Abstraction abs) {
            innercounter++;
            return new Abstraction(abs.body.accept(this),abs.name);
        }

        @Override
        public Term visit(Application app) {
            return new Application(
                    app.left.accept(new DebruijnFixWithVisitor(bvindex, innercounter)),
                    app.right.accept(new DebruijnFixWithVisitor(bvindex,innercounter))
            );
        }

        @Override
        public Term visit(BoundVariable bvar) {
            if (innercounter < bvar.index) {
                return new BoundVariable(bvindex + bvar.index - 1); //-1 so it isnt a hack
            } else {
                return bvar;
            }
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
    }


}
