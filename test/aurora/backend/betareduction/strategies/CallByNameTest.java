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
        RedexPath path =  cbn.getRedexPath(t);
        assertEquals(null, path);
    }

    // \x.((\s.s) a)
    @Test
    public void doNotFindRedex() throws Exception {
        Term t = new Abstraction(
                    new Application(
                            new Abstraction(
                                new BoundVariable(1),"s"
                            ),
                            new FreeVariable("a")
                    ),
                    "x"
        );
        CallByName cbn = new CallByName();
        RedexPath path = cbn.getRedexPath(t);
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
        CallByName cbn = new CallByName();
        RedexPath path = cbn.getRedexPath(t);
        LinkedList<RedexPath.Direction> list = path.getPath();
        assertEquals("[]",list.toString());
    }

}