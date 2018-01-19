package aurora.backend.betareduction.strategies;

import aurora.backend.RedexPath;
import aurora.backend.tree.*;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class NormalOrderTest {

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @Test
    public void TestNoRedex() throws Exception {
        Term t = new Abstraction(new BoundVariable(1), "s");
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedex(t);
        assertEquals(null, path);
    }

    @Test
    public void TestSimpleRedex() throws Exception {
        Term t = new Application(new Abstraction(new BoundVariable(1), "s"), new FreeVariable("a"));
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedex(t);
        LinkedList<RedexPath.Direction>list = path.getPath();
        assertEquals("[ROOT]",list.toString());
    }

    @Test
    public void RedexBehindAbs() throws Exception{
        Term t = new Abstraction(
                    new Application(
                            new Abstraction(new BoundVariable(1),"b"),
                            new FreeVariable("z")),
                "x");
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedex(t);
        LinkedList<RedexPath.Direction>list = path.getPath();
        assertEquals("[BODY, ROOT]",list.toString());

    }
    @Test
    public void RedexBeforeAbs() throws Exception {
        Term t = new Application(
                    new Abstraction(new BoundVariable(1),"x"),
                    new Application(
                            new FreeVariable("a"),
                            new FreeVariable("b")
                    )
        );
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedex(t);
        LinkedList<RedexPath.Direction>list = path.getPath();
        assertEquals("[ROOT]",list.toString());
    }
    @Test
    public void RedexAfterApp() throws Exception {
        Term t = new Application(
                    new Application(
                            new Abstraction(new BoundVariable(1),"x"),
                            new FreeVariable("a")
                    ),
                new FreeVariable("y")
        );
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedex(t);
        LinkedList<RedexPath.Direction>list = path.getPath();
        assertEquals("[LEFT, ROOT]",list.toString());
    }

    @Test
    public void RedexAfterappright() throws Exception {
        Term t = new Application(
                    new FreeVariable("y"),
                    new Application(
                            new Abstraction(new BoundVariable(1),"x"),
                            new FreeVariable("s")
                    )
        );
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedex(t);
        LinkedList<RedexPath.Direction>list = path.getPath();
        assertEquals("[RIGHT, ROOT]",list.toString());
    }

    @Test
    public void RedexAfterTwoAbs() throws Exception {
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
        RedexPath path = normal.getRedex(t);
        LinkedList<RedexPath.Direction>list = path.getPath();
        assertEquals("[BODY, BODY, ROOT]",list.toString());
    }
}