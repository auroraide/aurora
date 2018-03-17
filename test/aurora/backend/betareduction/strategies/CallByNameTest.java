package aurora.backend.betareduction.strategies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import aurora.backend.RedexPath;

import java.util.LinkedList;

import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;




public class CallByNameTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void noRedex() throws Exception {
        Term t = new Abstraction(new BoundVariable(1), "s");
        CallByName cbn = new CallByName();
        RedexPath path = cbn.getRedexPath(t);
        assertEquals(null, path);
    }

    // \x.((\s.s) a)
    @Test
    public void doNotFindRedex() throws Exception {
        Term t = new Abstraction(
                new Application(
                        new Abstraction(
                                new BoundVariable(1), "s"
                        ),
                        new FreeVariable("a")
                ),
                "x"
        );
        CallByName cbn = new CallByName();
        RedexPath path = cbn.getRedexPath(t);
        assertEquals(null, path);
    }

    @Test
    // (\y.y) ((\x.x)(\y.y)) this test is in all 3 strategies under the same name
    public void findAnotherRedex() {
        Term t = new Application(
                new Abstraction(
                        new BoundVariable(1), "y"
                ),
                new Application(
                        new Abstraction(
                                new BoundVariable(1), "x"
                        ),
                        new Abstraction(
                                new BoundVariable(1), "y"
                        )
                )
        );
        CallByName cbn = new CallByName();
        RedexPath path = cbn.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[]", list.toString());
    }

    @Test
    public void redexAfterApp() throws Exception {
        Term t = new Application(
                new Application(
                        new Abstraction(new BoundVariable(1), "x"),
                        new FreeVariable("a")
                ),
                new FreeVariable("y")
        );
        CallByName cbn = new CallByName();
        RedexPath path = cbn.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[LEFT]", list.toString());
    }

    @Test
    public void rightrightleft() {
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
        CallByName cbn = new CallByName();
        RedexPath path = cbn.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[RIGHT, RIGHT, LEFT]", list.toString());
    }

    @Test
    public void functiontest() {
        //(\x.x)y
        Function f = new Function("test", new Application(
                new Application(new Abstraction(new BoundVariable(1), "x"),
                        new FreeVariable("y")), new FreeVariable("s")));
        ChurchNumber c = new ChurchNumber(2);
        Term t = new Application(f, c);
        CallByName cbn = new CallByName();
        RedexPath path = cbn.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[LEFT, LEFT]", list.toString());


    }

    @Test
    public void churchtest() {
        Term t = new Application(new ChurchNumber(2), new ChurchNumber(3));
        CallByName cbn = new CallByName();
        RedexPath path = cbn.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[]", list.toString());


    }

    @Test
    public void dontgointochurch() {
        Term t = new ChurchNumber(5);
        CallByName cbn = new CallByName();
        RedexPath path = cbn.getRedexPath(t);
        assertEquals(null, path);
    }

    @Test
    public void traverseappsfindnothing() {
        Term t = new Application(new FreeVariable("x"), new FreeVariable("y"));
        CallByName cbn = new CallByName();
        RedexPath path = cbn.getRedexPath(t);
        assertEquals(null, path);
    }

    /**
     * This Terms should be impossible.
     */
    @Test
    public void boundVarWithoutAbs() {
        boolean ex = false;
        Term t = new BoundVariable(1);
        CallByName cb1 = new CallByName();
        try {
            RedexPath path1 = cb1.getRedexPath(t);

        } catch (RuntimeException e) {
            ex = true;
        }
        assertEquals(true,ex);
    }

    @Test
    public void boundvarInAppWithoutAbs() {
        boolean ex = false;
        Term f = new Application(new BoundVariable(1), new FreeVariable("y"));
        CallByName cbn2 = new CallByName();

        try {
            RedexPath path2 = cbn2.getRedexPath(f);
        } catch (RuntimeException e) {
            ex = true;
        }
        assertEquals(ex, true);
    }


}