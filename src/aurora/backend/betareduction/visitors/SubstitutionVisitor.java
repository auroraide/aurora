package aurora.backend.betareduction.visitors;

import aurora.backend.TermVisitor;
import aurora.backend.betareduction.Substitutor;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;

/**
 * Visitor that traverses the {@link Term} tree and substitutes a {@link BoundVariable} with a given {@link Term}.
 */
public class SubstitutionVisitor extends TermVisitor<Term> {

    private final Term with;

    private final Substitutor substitutor;

    /**
     * This constructor receives the replacement {@link Term}.
     *
     * @param with The term that will get substituted.
     */
    public SubstitutionVisitor(Term with) {
        this.with = with;
        this.substitutor = new Substitutor(with);
    }

    @Override
    public Term visit(Abstraction abs) {
        return this.substitutor.substitute(abs);
    }

    @Override
    public Term visit(Application app) {
        return this.substitutor.substitute(app);
    }

    @Override
    public Term visit(BoundVariable bvar) {
        return this.substitutor.substitute(bvar);
    }

    @Override
    public Term visit(FreeVariable fvar) {
        return this.substitutor.substitute(fvar);
    }

    @Override
    public Term visit(Function libterm) {
        return this.substitutor.substitute(libterm);
    }

    @Override
    public Term visit(ChurchNumber c) {
        return this.substitutor.substitute(c);
    }

}
