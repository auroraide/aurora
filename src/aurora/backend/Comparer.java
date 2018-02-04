package aurora.backend;

import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;

import java.util.LinkedList;

/**
 * Takes two terms and checks if they are the same.
 */
public class Comparer {

    private Term t1;

    private Term t2;

    private LinkedList<String> listt1;

    private LinkedList<String> listt2;

    /**
     * Takes two terms and checks if they are the same. I use it for debugging, you can too.
     * Use the compare method to check.
     *
     * @param t1 the first term
     * @param t2 the second term
     */
    public Comparer(Term t1, Term t2) {
        this.t1 = t1;
        this.t2 = t2;
        listt1 = new LinkedList<>();
        listt2 = new LinkedList<>();
    }

    /**
     * Use this method to check if the terms you gave the constructor are identical.
     *
     * @return true if identical, false if not identical
     */
    public boolean compare() {
        CompareVisitor cvt1 = new CompareVisitor(1);
        CompareVisitor cvt2 = new CompareVisitor(2);

        t1.accept(cvt1);
        t2.accept(cvt2);

        if (listt1.size() == 0 || listt2.size() == 0) {
            System.out.println("One of the Terms is empty");
            return false;
        }
        if (listt1.size() != listt2.size()) {
            System.out.println("The terms are not identical, one is bigger");
            System.out.println("list1 = " + listt1.toString() + " list2= " + listt2.toString());
            return false;
        }
        for (int a = 0; a < listt1.size(); a++) {
            if (!listt1.get(a).equals(listt2.get(a))) {
                System.out.println("Term 1 has " + listt1.get(a) + " but Term 2 has " + listt2.get(a));
                System.out.println("list 1 =" + listt1.toString());
                System.out.println("list 2 = " + listt2.toString());
                return false;
            }
        }

        return true;
    }

    private class CompareVisitor extends TermVisitor<Void> {

        private LinkedList<String> list;

        CompareVisitor(int x) {
            if (x == 1) {
                this.list = listt1;
            } else {
                this.list = listt2;
            }
        }

        @Override
        public Void visit(Abstraction abs) {
            list.add("abs" + abs.name);
            abs.body.accept(this);
            return null;
        }

        @Override
        public Void visit(Application app) {
            list.add("app");
            app.left.accept(this);
            app.right.accept(this);
            return null;
        }

        @Override
        public Void visit(BoundVariable bvar) {
            list.add("bv" + Integer.toString(bvar.index));
            return null;
        }

        @Override
        public Void visit(FreeVariable fvar) {
            list.add("fv" + fvar.name);
            return null;
        }

        @Override
        public Void visit(Function libterm) {
            list.add("lb" + libterm.name);
            return null;
        }

        @Override
        public Void visit(ChurchNumber c) {
            list.add("cn" + Integer.toString(c.value));
            return null;
        }

    }

}
