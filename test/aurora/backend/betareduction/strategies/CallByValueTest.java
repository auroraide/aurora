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



public class CallByValueTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findNoRedex() {
        Term t = new Abstraction(
                new Application(
                        new Abstraction(
                                new BoundVariable(1),"s"
                        ),
                        new FreeVariable("a")
                ),
                "x"
        );
        CallByValue cbv = new CallByValue();
        RedexPath path = cbv.getRedexPath(t);
        assertEquals(null,path);
    }

    @Test
    // (\y.y) ((\x.x)(\y.y)) this test is in all 3 strategies under the same name
    public void findAnotherRedex() {
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
        CallByValue cbv = new CallByValue();
        RedexPath path = cbv.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[RIGHT]",list.toString());
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
        CallByValue cbv = new CallByValue();
        RedexPath path = cbv.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[RIGHT, RIGHT, LEFT]",list.toString());
    }

    @Test
    public void noredexleftleft() {
        Term t = new Application(
                    new Application(
                            new FreeVariable("y"), new FreeVariable("x")
                    ), new FreeVariable("z")
        );

        CallByValue cbv = new CallByValue();
        RedexPath path = cbv.getRedexPath(t);
        assertEquals(null, path);
    }

    @Test
    public void functionright() {
        Function f = new Function("test", new Application(new Abstraction(new BoundVariable(1),"x"),
                new FreeVariable("y")));
        Term t = new Application(new FreeVariable("y"),f);
        CallByValue cbv = new CallByValue();
        RedexPath path = cbv.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[RIGHT]",list.toString());
    }

    @Test
    public void functionleft() {
        Function f = new Function("test", new Application(new Abstraction(new BoundVariable(1),"x"),
                new FreeVariable("y")));
        Term t = new Application(f,new FreeVariable("y"));
        CallByValue cbv = new CallByValue();
        RedexPath path = cbv.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[LEFT]",list.toString());
    }

    @Test
    public void ischurchabs() {
        Term t = new Application(new ChurchNumber(2), new FreeVariable("x"));
        CallByValue cbv = new CallByValue();
        RedexPath path = cbv.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[]",list.toString());
    }

    @Test
    public void ischurchvalue() {
        Term t = new Application(new FreeVariable("x"),new ChurchNumber(2));
        CallByValue cbv = new CallByValue();
        RedexPath path = cbv.getRedexPath(t);
        assertEquals(null,path);
    }
}


