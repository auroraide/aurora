package aurora.backend.betareduction;

import aurora.backend.RedexPath;
import aurora.backend.TermVisitor;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;

import java.util.Iterator;
import java.util.Stack;

/**
 * Don't judge me please.
 */
public class Replacer {

    private final Term with;

    private final Iterator<RedexPath.Direction> pathIterator;

    private final Stack<Term> termStack;
    private final Stack<Call> callStack;
    private final Stack<String> nameStack;

    /**
     * Replace an {@link Application} term with some other {@link Term}.
     *
     * @param path Path to application that shall be replaced.
     * @param with Replacement term.
     */
    public Replacer(RedexPath path, Term with) {
        this.with = with;
        this.pathIterator = path.iterator();
        this.termStack = new Stack<>();
        this.callStack = new Stack<>();
        this.nameStack = new Stack<>();

    }

    /**
     * Perform the replacement on the given subject {@link Term}.
     *
     * @param subject {@link Term} upon which the replacement shall be performed.
     * @return The new {@link Term}.
     */
    public Term replace(Term subject) {
        this.callStack.push(Call.CALL_REPLACE);
        this.termStack.push(subject);

        ReplaceVisitor rv = new ReplaceVisitor();

        while (!this.callStack.isEmpty()) {
            Call call = this.callStack.pop();

            switch (call) {
                case CALL_REPLACE:
                    this.termStack.pop().accept(rv);
                    break;
                case CALL_APPLICATION:
                    Term right = this.termStack.pop();
                    Term left = this.termStack.pop();
                    this.termStack.push(new Application(left, right));
                    break;
                case CALL_APPLICATION_INVERTED:
                    Term l = this.termStack.pop();
                    Term r = this.termStack.pop();
                    this.termStack.push(new Application(l, r));
                    break;
                case CALL_ABSTRACTION:
                    String name = this.nameStack.pop();
                    Term body = this.termStack.pop();
                    this.termStack.push(new Abstraction(body, name));
                    break;
                default:
            }
        }

        return this.termStack.pop();
    }

    private class ReplaceVisitor extends TermVisitor<Void> {

        @Override
        public Void visit(Abstraction abs) {
            callStack.push(Call.CALL_ABSTRACTION);
            callStack.push(Call.CALL_REPLACE);
            termStack.push(abs.body);
            nameStack.push(abs.name);
            return null;
        }

        @Override
        public Void visit(Application app) {
            if (!pathIterator.hasNext()) {
                termStack.push(with);
                return null;
            }

            if (pathIterator.next() == RedexPath.Direction.LEFT) {
                callStack.push(Call.CALL_APPLICATION_INVERTED);
                callStack.push(Call.CALL_REPLACE);
                termStack.push(app.right);
                termStack.push(app.left);
                return null;
            }

            callStack.push(Call.CALL_APPLICATION);
            callStack.push(Call.CALL_REPLACE);
            termStack.push(app.left);
            termStack.push(app.right);
            return null;
        }

        @Override
        public Void visit(BoundVariable bvar) {
            assert false;
            return null;
        }

        @Override
        public Void visit(FreeVariable fvar) {
            assert false;
            return null;
        }

        @Override
        public Void visit(Function libterm) {
            callStack.push(Call.CALL_REPLACE);
            termStack.push(libterm.term);
            return null;
        }

        @Override
        public Void visit(ChurchNumber c) {
            Abstraction abs = c.getAbstraction();
            callStack.push(Call.CALL_ABSTRACTION);
            callStack.push(Call.CALL_REPLACE);
            termStack.push(abs.body);
            nameStack.push(abs.name);
            return null;
        }

    }

    private enum Call {

        CALL_APPLICATION,
        CALL_APPLICATION_INVERTED,
        CALL_ABSTRACTION,
        CALL_REPLACE

    }

}
