package aurora.backend;

import aurora.backend.tree.Abstraction;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShareLaTeXTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testlambdasymbol() {
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(new Abstraction(
                new BoundVariable(1),"x"));
        ShareLaTeX sh = new ShareLaTeX(hle);
        assertEquals("$\\lambda x . x ",sh.generateLaTeX());
    }

    @Test
    public void dollar() {
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(new Function(
                "test", new FreeVariable("x")));
        ShareLaTeX sh = new ShareLaTeX(hle);
        assertEquals(sh.generateLaTeX(), "\\$test ");
    }
}