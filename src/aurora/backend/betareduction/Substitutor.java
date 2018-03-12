package aurora.backend.betareduction;

import aurora.backend.TermVisitor;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;

import java.util.Stack;

/**
 * This is the worst.
 */
public class Substitutor {

    private final Stack<String> nameStack;
    private final Stack<Call> callStack;
    private final Stack<Term> termStack;
    private final Stack<Integer> indexStack;

    private Term with;


    /**
     * Blah Blah.
     * @param with Blah.
     */
    public Substitutor(Term with) {
        this.with = with;

        this.termStack = new Stack<>();
        this.callStack = new Stack<>();
        this.nameStack = new Stack<>();
        this.indexStack = new Stack<>();
    }

    /**
     * Blah Blah.
     * @param subject Blah.
     * @return Blah.
     */
    public Term substitute(Term subject) {
        this.callStack.push(Call.CALL_SUBSTITUTE);
        this.termStack.push(subject);
        this.indexStack.push(0);

        Stack<Term> saved = new Stack<>();

        while (!this.callStack.isEmpty()) {
            Call call = this.callStack.pop();
            switch (call) {
                case CALL_APPLICATION:
                    Term right = this.termStack.pop();
                    Term left = this.termStack.pop();
                    this.termStack.push(new Application(left, right));
                    break;
                case CALL_ABSTRACTION:
                    String name = this.nameStack.pop();
                    Term body = this.termStack.pop();
                    this.termStack.push(new Abstraction(body, name));
                    break;
                case CALL_BOUND_VARIABLE:
                    int index = this.indexStack.pop();
                    this.termStack.push(new BoundVariable(index));
                    break;
                case CALL_DEBRUIJN_FIX:
                    int innercounter = this.indexStack.pop();
                    int bvindex = this.indexStack.pop();
                    this.termStack.pop().accept(new DebruijnVisitor(bvindex, innercounter));
                    break;
                case CALL_SUBSTITUTE:
                    int idx = this.indexStack.pop();
                    this.termStack.pop().accept(new SubstituteVisitor(idx));
                    break;
                case STACK_SAVE:
                    saved.push(termStack.pop());
                    break;
                case STACK_RESTORE:
                    termStack.push(saved.pop());
                    break;
                default:
            }
        }

        return this.termStack.pop();
    }

    private class SubstituteVisitor extends TermVisitor<Void> {

        private int index;

        public SubstituteVisitor(int index) {
            this.index = index;
        }

        @Override
        public Void visit(Abstraction abs) {
            callStack.push(Call.CALL_ABSTRACTION);
            callStack.push(Call.CALL_SUBSTITUTE);
            indexStack.push(index + 1);
            termStack.push(abs.body);
            nameStack.push(abs.name);
            return null;
        }

        @Override
        public Void visit(Application app) {
            callStack.push(Call.CALL_APPLICATION);
            callStack.push(Call.STACK_RESTORE);
            callStack.push(Call.CALL_SUBSTITUTE);
            callStack.push(Call.STACK_SAVE);
            callStack.push(Call.CALL_SUBSTITUTE);
            indexStack.push(index);
            termStack.push(app.left);
            indexStack.push(index);
            termStack.push(app.right);
            return null;
        }

        @Override
        public Void visit(BoundVariable bvar) {
            if (bvar.index == index) {
                callStack.push(Call.CALL_DEBRUIJN_FIX);
                termStack.push(with);
                indexStack.push(bvar.index);
                indexStack.push(0);
                return null;
            }

            if (bvar.index > index) {
                callStack.push(Call.CALL_BOUND_VARIABLE);
                indexStack.push(bvar.index - 1);
                return null;
            }

            termStack.push(bvar);
            return null;
        }

        @Override
        public Void visit(FreeVariable fvar) {
            termStack.push(fvar);
            return null;
        }

        @Override
        public Void visit(Function libterm) {
            callStack.push(Call.CALL_SUBSTITUTE);
            termStack.push(libterm.term);
            indexStack.push(index);
            return null;
        }

        @Override
        public Void visit(ChurchNumber c) {
            callStack.push(Call.CALL_SUBSTITUTE);
            termStack.push(c.getAbstraction());
            indexStack.push(index);
            return null;
        }

    }

    private class DebruijnVisitor extends TermVisitor<Void> {

        int bvindex;

        int innercounter;

        public DebruijnVisitor(int bvindex, int innercounter) {
            this.bvindex = bvindex;
            this.innercounter = innercounter;
        }

        @Override
        public Void visit(Abstraction abs) {
            callStack.push(Call.CALL_ABSTRACTION);
            callStack.push(Call.CALL_DEBRUIJN_FIX);
            termStack.push(abs.body);
            indexStack.push(bvindex);
            indexStack.push(innercounter + 1);
            nameStack.push(abs.name);
            return null;
        }

        @Override
        public Void visit(Application app) {
            callStack.push(Call.CALL_APPLICATION);
            callStack.push(Call.STACK_RESTORE);
            callStack.push(Call.CALL_DEBRUIJN_FIX);
            callStack.push(Call.STACK_SAVE);
            callStack.push(Call.CALL_DEBRUIJN_FIX);
            termStack.push(app.left);
            indexStack.push(bvindex);
            indexStack.push(innercounter);
            termStack.push(app.right);
            indexStack.push(bvindex);
            indexStack.push(innercounter);
            return null;
        }

        @Override
        public Void visit(BoundVariable bvar) {

            if (innercounter < bvar.index) {
                callStack.push(Call.CALL_BOUND_VARIABLE);
                indexStack.push(bvindex + bvar.index - 1);
                return null;
            }

            termStack.push(bvar);
            return null;
        }

        @Override
        public Void visit(FreeVariable fvar) {
            termStack.push(fvar);
            return null;
        }

        @Override
        public Void visit(Function libterm) {
            termStack.push(libterm);
            return null;
        }

        @Override
        public Void visit(ChurchNumber c) {
            termStack.push(c);
            return null;
        }

    }

    private enum Call {

        CALL_APPLICATION,
        CALL_ABSTRACTION,
        CALL_BOUND_VARIABLE,
        CALL_DEBRUIJN_FIX,
        CALL_SUBSTITUTE,
        STACK_SAVE,
        STACK_RESTORE

    }

}
