package aurora.backend;

import static org.junit.Assert.assertEquals;

import aurora.backend.betareduction.strategies.NormalOrder;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Term;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


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
                                new Application(new Abstraction(new BoundVariable(1),"a"),
                                        new FreeVariable("z")),
                                new FreeVariable("h")
                        )
                )
        );
        NormalOrder normal = new NormalOrder();
        RedexPath path = normal.getRedexPath(t);
        Application app = path.get(t);
        FreeVariable fv = (FreeVariable) app.right;
        assertEquals("z",fv.name);
        assertEquals("Application",path.getParenttype());
    }


}