package aurora.backend;

import aurora.backend.parser.LambdaParser;
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
        assertEquals("\\ s . \\ z . s ( s z ) ", hle.toString());

    }

    @Test
    public void withparenscauseabs() {
        Term t = new Application(
                new Abstraction(new BoundVariable(1),"x"), new FreeVariable("y")
        );
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("( \\ x . x ) y ",hle.toString());
    }

    @Test
    public void doublealpha() {
        Term t = new Abstraction(
                new Abstraction(
                        new BoundVariable(1),"x"
                ),"x"
        );

        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("\\ x . \\ x . x ", hle.toString());
    }

    @Test
    public void triplealpha() {
        Term t = new Abstraction(
                    new Abstraction(
                            new Abstraction(
                                    new BoundVariable(1),"x"
                            ),"x"
                    ),"x"
        );
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("\\ x . \\ x . \\ x . x ",hle.toString());
    }

    @Test
    public void fvaralpha() {
        Term t = new Abstraction(
                        new Abstraction(
                                new Application(
                                        new BoundVariable(1), new FreeVariable("x")
                                ),"x"
                        ),"x"
        );
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("\\ x . \\ x . x x1 ", hle.toString());
    }

    @Test
    // just print the boundedvars
    public void infinity() {
        Term t = new Application(
                new Abstraction(
                        new Application(
                                new BoundVariable(1), new BoundVariable(1)
                        ), "x"
                ),
                new Abstraction(
                        new Application(
                                new BoundVariable(1), new BoundVariable(1)
                        ), "x"
                )
        );

        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("( \\ x . x x ) ( \\ x . x x ) ",hle.toString());

    }

    @Test
    public void morealpha() {
        Term t = new Abstraction(
                new Application(
                        new BoundVariable(1), new FreeVariable("x")
                ),"x"
        );
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("\\ x . x x1 ",hle.toString());
    }

}
