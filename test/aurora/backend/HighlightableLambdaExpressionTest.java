package aurora.backend;

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

/**
 * these tests fail if you improve hle.
 */
public class HighlightableLambdaExpressionTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onetoken() {
        Term t = new FreeVariable("x");
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("x ",hle.toString());

    }

    @Test
    public void withparenscauseapp() {
        Abstraction t = new ChurchNumber(2).getAbstraction();
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("\\ s . \\ z . 2 ( 2 1 ) ", hle.toString());

    }

    @Test
    public void withparenscauseabs() {
        Term t = new Application(
                new Abstraction(new BoundVariable(1),"x"), new FreeVariable("y")
        );
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("( \\ x . 1 ) y ",hle.toString());
    }

}