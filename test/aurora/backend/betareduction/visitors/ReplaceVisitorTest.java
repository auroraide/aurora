package aurora.backend.betareduction.visitors;

import aurora.backend.RedexPath;
import aurora.backend.betareduction.strategies.NormalOrder;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Term;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReplaceVisitorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void canthappenbvar() {
        boolean ex = false;
        Term errorterm = new BoundVariable(1);
        Term goodterm = new Application(new Abstraction(new BoundVariable(1), "x"),
                new FreeVariable("a"));
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(goodterm);
        ReplaceVisitor v = new ReplaceVisitor(path, new FreeVariable("a"));

        try {
            errorterm.accept(v);
        } catch (RuntimeException e) {
            ex = true;
        }
        assertEquals(true, ex);
    }

    @Test
    public void canthappenfvar() {
        boolean ex = false;
        Term errorterm = new FreeVariable("a");
        Term goodterm = new Application(new Abstraction(new BoundVariable(1), "x"),
                new FreeVariable("a"));
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(goodterm);
        ReplaceVisitor v = new ReplaceVisitor(path, new FreeVariable("a"));

        try {
            errorterm.accept(v);
        } catch (RuntimeException e) {
            ex = true;
        }
        assertEquals(true, ex);
    }

    @Test
    public void canthappenchurch() {
        boolean ex = false;
        Term errorterm = new ChurchNumber(5);
        Term goodterm = new Application(new Abstraction(new BoundVariable(1), "x"),
                new FreeVariable("a"));
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(goodterm);
        ReplaceVisitor v = new ReplaceVisitor(path, new FreeVariable("a"));

        try {
            errorterm.accept(v);
        } catch (RuntimeException e) {
            ex = true;
        }
        assertEquals(true, ex);
    }
}