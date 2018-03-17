package aurora.backend;

import static org.junit.Assert.assertEquals;

import aurora.backend.betareduction.BetaReducer;
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

import java.util.LinkedList;


public class RedexPathTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void appfinder() {
        Term t = new Application(
                new FreeVariable("s"),
                new Application(
                        new FreeVariable("y"),
                        new Application(
                                new Application(new Abstraction(new BoundVariable(1), "a"),
                                        new FreeVariable("z")),
                                new FreeVariable("h")
                        )
                )
        );
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        Application app = path.get(t);
        FreeVariable fv = (FreeVariable) app.right;
        assertEquals("z", fv.name);
    }



    @Test
    public void covpath() {

        Term ogt = new Application(new Abstraction(new BoundVariable(1), "x"), new FreeVariable("y"));
        RedexPath ogpath = new NormalOrder().getRedexPath(ogt);

        RedexPath path = new RedexPath(ogpath.getPath());

        boolean ex = false;
        try {
            path.get(new ChurchNumber(2));
        } catch (RuntimeException e) {
            ex = true;
        }
        assertEquals(true, ex);

        boolean ex1 = false;
        try {
            path.get(new Abstraction(new BoundVariable(1), "sy"));
        } catch (RuntimeException e) {
            ex1 = true;
        }
        assertEquals(true, ex1);

        boolean ex2 = false;
        try {
            path.get(new FreeVariable("a"));
        } catch (RuntimeException e) {
            ex2 = true;
        }
        assertEquals(true, ex2);
    }

    @Test
    public void emptypath() {
        LinkedList<RedexPath.Direction> l = new LinkedList<RedexPath.Direction>();
        l = null;
        RedexPath rp = new RedexPath(l);
        boolean ex = false;
        try {
            Application a = rp.get(new FreeVariable("x"));
        } catch (RuntimeException e) {
            ex = true;
        }
        assertEquals(true, ex);
    }

    @Test
    public void same() {
        Term t = new Application(new Abstraction(new BoundVariable(1), "a"), new FreeVariable("y"));
        Term f = new Abstraction(new Application(new Abstraction(
                    new BoundVariable(1), "x"), new FreeVariable("z")), "a");
        RedexPath rone = new NormalOrder().getRedexPath(t);
        RedexPath rtwo = new NormalOrder().getRedexPath(f);

        assertEquals(rone.isSame(rtwo), true);

        Term a = new Application(new Application(new Abstraction(
                new BoundVariable(1), "y"), new FreeVariable("a")), new FreeVariable("y"));
        RedexPath rthree = new NormalOrder().getRedexPath(a);

        assertEquals(rone.isSame(rthree), false);

        Term b = new Application(new FreeVariable("y"), new Application(
                new Abstraction(new BoundVariable(1), "y"), new FreeVariable("y")));
        RedexPath rfour = new NormalOrder().getRedexPath(b);
        assertEquals(rthree.isSame(rfour), false);


    }


}