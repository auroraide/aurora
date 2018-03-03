package aurora.backend.simplifier;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.tree.*;
import com.google.gwt.dev.jjs.impl.Simplifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChurchNumberSimplifierTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isachurcheasy() {
        Term zero = new ChurchNumber(0);
        Term t = new ChurchNumber(1);
        ChurchNumberSimplifier simplifier = new ChurchNumberSimplifier();
        ChurchNumber resultone = simplifier.simplify(t);
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(resultone);
        assertEquals("1 ", hle.toString());

        ChurchNumber resulttwo = simplifier.simplify(new ChurchNumber(2));
        HighlightableLambdaExpression hle2 = new HighlightableLambdaExpression(resulttwo);
        assertEquals("2 ", hle2.toString());

        ChurchNumber resultfive = simplifier.simplify(new ChurchNumber(5));
        HighlightableLambdaExpression hle5 = new HighlightableLambdaExpression(resultfive);
        assertEquals("5 ", hle5.toString());

        Term zeroresult = simplifier.simplify(zero);
        HighlightableLambdaExpression hle0 = new HighlightableLambdaExpression(zeroresult);
        assertEquals("0 ", hle0.toString());
    }

    /**
     * \x. \y x (x y ) is also a church
     */
    @Test
    public void isachurchwithdifferentname() {

        Term t = new Abstraction(
                new Abstraction(
                        new Application(
                                new BoundVariable(2), new Application(
                                        new BoundVariable(2), new BoundVariable(1))
                        ), "y"
                        ), "x"
        );
        ChurchNumberSimplifier simplifier = new ChurchNumberSimplifier();
        Term result = simplifier.simplify(t);
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(result);
        assertEquals("2 ", hle.toString());
    }

    @Test
    public void notachurch() {
        Term t = new Abstraction(
                new Application(
                        new BoundVariable(2), new BoundVariable(2)
                ), "x"
        );
        ChurchNumberSimplifier simple1 = new ChurchNumberSimplifier();
        Term result1 = simple1.simplify(t);
        assertEquals(null, result1);

        Term f = new FreeVariable("x");
        result1 = simple1.simplify(f);
        assertEquals(null, result1);

        Term x = new Abstraction(
                new Abstraction(
                        new BoundVariable(2), "x"
                ), "x"
        );
        result1 = simple1.simplify(x);
        assertEquals(null, result1);

        Term y = new Abstraction(
                new Abstraction(
                        new Application(
                                new BoundVariable(1), new BoundVariable(1)
                        ), "x"
                ), "y");
        result1 = simple1.simplify(y);
        assertEquals(null,result1);

    }
}