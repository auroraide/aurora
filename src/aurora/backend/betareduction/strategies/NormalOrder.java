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

import java.util.Stack;

/**
 * The Normal order is the default reduction strategy. It always selects the leftmost redex.
 */
public class NormalOrder extends ReductionStrategy {

    private final Stack<Call> callStack;
    private final Stack<Term> termStack;
    private final Stack<Boolean> boolStack;
    private final Stack<Integer> intStack;
    private final Stack<Integer> returnStack;

    private final RedexPath redexPath;

    /**
     * asdf.
     */
    public NormalOrder() {
        this.callStack = new Stack<>();
        this.termStack = new Stack<>();
        this.boolStack = new Stack<>();
        this.intStack = new Stack<>();
        this.returnStack = new Stack<>();

        this.redexPath = new RedexPath();
    }

    private void saveStack() {
        this.returnStack.push(this.callStack.size());
        this.returnStack.push(this.termStack.size());
        this.returnStack.push(this.boolStack.size());
        this.returnStack.push(this.intStack.size());
    }

    private void restoreStack() {
        int is = this.returnStack.pop();
        int bs = this.returnStack.pop();
        int ts = this.returnStack.pop();
        int cs = this.returnStack.pop();

        assert this.intStack.size() >= is;
        assert this.boolStack.size() >= bs;
        assert this.termStack.size() >= ts;
        assert this.callStack.size() >= cs;

        while (this.intStack.size() > is) this.intStack.pop();
        while (this.boolStack.size() > is) this.boolStack.pop();
        while (this.termStack.size() > is) this.termStack.pop();
        while (this.callStack.size() > is) this.callStack.pop();
    }

    private void discardStack() {
        this.returnStack.pop();
        this.returnStack.pop();
        this.returnStack.pop();
        this.returnStack.pop();
    }

    @Override
    public RedexPath getRedexPath(Term t) {
        callStack.push(Call.CALL_FIND_REDEX);
        termStack.push(t);

        RedexFinderVisitor redexFinder = new RedexFinderVisitor();
        AbstractionFinder absFinder = new AbstractionFinder();

        Term eax = null;
        boolean ebx = false;

        while (!this.callStack.isEmpty()) {
            Call call = this.callStack.pop();
            switch (call) {
                case CALL_APPLICATION:
                    break;
                case CALL_ABSTRACTION:
                    break;
                case CALL_BOUND_VARIABLE:
                    break;
                case CALL_FIND_REDEX:
                    eax = termStack.pop();
                    eax.accept(redexFinder);
                    break;
                case CALL_FIND_ABSTRACTION:
                    eax = termStack.pop();
                    eax.accept(absFinder);
                    break;
                case CALL_PATH_PUSH_LEFT:
                    redexPath.push(RedexPath.Direction.LEFT);
                    break;
                case CALL_PATH_PUSH_RIGHT:
                    redexPath.push(RedexPath.Direction.RIGHT);
                    break;
                case CALL_RETURN_IF_FOUND:
                    break;
                case STACK_SAVE:
                    break;
                case JUMP_IF:
                    break;
                case CALL_PATH_POP:
                    redexPath.pop();
                    break;
                case RETURN_BOOL:
                    ebx = boolStack.pop();
                    restoreStack();
                    boolStack.push(ebx);
                    break;
                case IF:
                    ebx = boolStack.pop();
                    if (ebx) {
                        discardStack();
                    } else {
                        restoreStack();
                    }
                    break;
                case RETURN_TRUE:
                    restoreStack();
                    boolStack.push(true);
                    break;
                case RETURN_FALSE:
                    restoreStack();
                    boolStack.push(false);
                    break;
                case STACK_RESTORE:
                    break;
                default:
            }
        }

        // return redex path if found
        if (boolStack.pop()) {
            return this.redexPath;
        }
        return null;
    }

    /**
     * Traverses the tree looking for next redex.
     */
    private class RedexFinderVisitor extends TermVisitor<Void> {

        @Override
        public Void visit(Abstraction abs) {
            callStack.push(Call.CALL_FIND_REDEX);
            termStack.push(abs.body);
            return null;
        }

        @Override
        public Void visit(Application app) {
            saveStack();

            callStack.push(Call.RETURN_FALSE);

            callStack.push(Call.CALL_PATH_POP);

            saveStack();
            callStack.push(Call.RETURN_TRUE);
            callStack.push(Call.IF);

            callStack.push(Call.CALL_FIND_REDEX);
            termStack.push(app.right);

            callStack.push(Call.CALL_PATH_PUSH_RIGHT);
            callStack.push(Call.CALL_PATH_POP);

            saveStack();
            callStack.push(Call.RETURN_TRUE);
            callStack.push(Call.IF);

            callStack.push(Call.CALL_FIND_REDEX);
            termStack.push(app.left);

            callStack.push(Call.CALL_PATH_PUSH_LEFT);

            saveStack();
            callStack.push(Call.RETURN_TRUE);
            callStack.push(Call.IF);

            callStack.push(Call.CALL_FIND_ABSTRACTION);
            termStack.push(app.left);

            return null;
        }

        @Override
        public Void visit(BoundVariable bvar) {
            boolStack.push(false);
            return null;
        }

        @Override
        public Void visit(FreeVariable fvar) {
            boolStack.push(false);
            return null;
        }

        @Override
        public Void visit(Function function) {
            callStack.push(Call.CALL_FIND_REDEX);
            termStack.push(function.term);
            return null;
        }

        @Override
        public Void visit(ChurchNumber c) {
            boolStack.push(true);
            return null;
        }

    }

    /**
     * Visitor that helps find abstractions inside our Term tree.
     */
    private class AbstractionFinder extends TermVisitor<Void> {

        @Override
        public Void visit(Abstraction abs) {
            boolStack.push(true);
            return null;
        }

        @Override
        public Void visit(Application app) {
            boolStack.push(false);
            return null;
        }

        @Override
        public Void visit(BoundVariable bvar) {
            boolStack.push(false);
            return null;
        }

        @Override
        public Void visit(FreeVariable fvar) {
            boolStack.push(false);
            return null;
        }

        @Override
        public Void visit(Function function) {
            callStack.push(Call.CALL_FIND_ABSTRACTION);
            termStack.push(function.term);
            return null;
        }

        @Override
        public Void visit(ChurchNumber c) {
            boolStack.push(true);
            return null;
        }

    }

    private enum Call {

        CALL_APPLICATION,
        CALL_ABSTRACTION,
        CALL_BOUND_VARIABLE,
        CALL_FIND_REDEX,
        CALL_FIND_ABSTRACTION,
        CALL_PATH_PUSH_LEFT,
        CALL_PATH_PUSH_RIGHT,
        CALL_RETURN_IF_FOUND,
        STACK_SAVE,
        JUMP_IF, CALL_PATH_POP, RETURN_BOOL, IF, RETURN_TRUE, RETURN_FALSE, STACK_RESTORE

    }

}
