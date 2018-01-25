package aurora.backend.betareduction.strategies;

import static org.junit.Assert.assertEquals;

import aurora.backend.RedexPath;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Term;
import java.util.LinkedList;
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
        RedexPath path = cbv.getRedex(t);
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
        RedexPath path = cbv.getRedex(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        System.out.println(list.toString());
    }
}


