package aurora.backend.betareduction.strategies;

import static org.junit.Assert.assertEquals;

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
import org.junit.Test;


public class NormalOrderTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testNoRedex() throws Exception {
        Term t = new Abstraction(new BoundVariable(1), "s");
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        assertEquals(null, path);
    }

    @Test
    public void testSimpleRedex() throws Exception {
        Term t = new Application(new Abstraction(new BoundVariable(1), "s"), new FreeVariable("a"));
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[]",list.toString());
    }

    @Test
    public void redexBehindAbs() throws Exception {
        Term t = new Abstraction(
                    new Application(
                            new Abstraction(new BoundVariable(1),"b"),
                            new FreeVariable("z")),
                "x");
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[]",list.toString());

    }

    @Test
    public void redexBeforeAbs() throws Exception {
        Term t = new Application(
                    new Abstraction(new BoundVariable(1),"x"),
                    new Application(
                            new FreeVariable("a"),
                            new FreeVariable("b")
                    )
        );
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[]",list.toString());
    }

    @Test
    public void redexAfterApp() throws Exception {
        Term t = new Application(
                    new Application(
                            new Abstraction(new BoundVariable(1),"x"),
                            new FreeVariable("a")
                    ),
                new FreeVariable("y")
        );
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[LEFT]",list.toString());
    }

    @Test
    public void redexAfterappright() throws Exception {
        Term t = new Application(
                    new FreeVariable("y"),
                    new Application(
                            new Abstraction(new BoundVariable(1),"x"),
                            new FreeVariable("s")
                    )
        );
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[RIGHT]",list.toString());
    }

    @Test
    public void redexAfterTwoAbs() throws Exception {
        Term t = new Abstraction(
                    new Abstraction(
                            new Application(
                                    new Abstraction(
                                            new BoundVariable(1),"x"
                                    ),
                                    new FreeVariable("a")
                            ),"z"
                    ), "y"
        );
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[]",list.toString());
    }

    // \x.((\s.s) a)
    @Test
    public void findRedex() throws Exception {
        Term t = new Abstraction(
                new Application(
                        new Abstraction(
                                new BoundVariable(1),"s"
                        ),
                        new FreeVariable("a")
                ),
                "x"
        );
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[]",list.toString());
    }

    @Test
    // (\y.y) ((\x.x)(\y.y)) this test is in all 3 strategies under the same name
    public  void findAnotherRedex() throws Exception {
        Term t = new Application(
                new Abstraction(
                        new BoundVariable(1),"y"
                ),
                new Application(
                        new Abstraction(
                                new BoundVariable(1),"x"
                        ),
                        new Abstraction(
                                new BoundVariable(1),"y"
                        )
                )
        );
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[]",list.toString());
    }

    @Test
    //churchnumbertest
    public void churchTest() throws Exception {
        Term t = new Application(
                    new ChurchNumber(3),
                    new FreeVariable("s")
        );
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[]",list.toString());
    }

    @Test
    public void rightrightleft() {
        Term t = new Application(
                new FreeVariable("s"),
                new Application(
                        new FreeVariable("y"),
                        new Application(
                                new Application(new Abstraction(new BoundVariable(1),"a"),
                                        new FreeVariable("z")),
                                new FreeVariable("h")
                        )
                )
        );
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[RIGHT, RIGHT, LEFT]",list.toString());
    }

    @Test
    public void libterm() {
        Function function = new Function("test", new Application(
                new Application(
                        new Abstraction(new BoundVariable(1),"x"),
                        new FreeVariable("a")
                ),
                new FreeVariable("y")
        ));
        Term t = function;
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[LEFT]",list.toString());
    }

    @Test
    public void libinapp() {
        Function function = new Function("test", new Abstraction(
                new BoundVariable(1),"x"
        ));
        Term t = new Application(function,new FreeVariable("s"));
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[]",list.toString());
    }

    @Test
    public void traverseappsfindnothing() {
        Term t = new Application(new FreeVariable("x"), new FreeVariable("y"));
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        assertEquals(null, path);
    }

    @Test
    public void church() {
        Term t = new ChurchNumber(3);
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        assertEquals(null, path);
    }
}