package aurora.backend.simplifier;

import aurora.backend.HighlightableLambdaExpression;
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

        Term t = new ChurchNumber(1);
        ChurchNumberSimplifier simplifier = new ChurchNumberSimplifier();
        ChurchNumber resultone = simplifier.simplify(t);
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(resultone);
        assertEquals("1", hle.toString());

        ChurchNumber resulttwo = simplifier.simplify(new ChurchNumber(2));
        HighlightableLambdaExpression hle2 = new HighlightableLambdaExpression(resulttwo);
        assertEquals("2", hle2.toString());

        ChurchNumber resultfive = simplifier.simplify(new ChurchNumber(5));
        HighlightableLambdaExpression hle5 = new HighlightableLambdaExpression(resultfive);
        assertEquals("5", hle5.toString());
        Term zero = new ChurchNumber(0);
        Term zeroresult = simplifier.simplify(zero);
        HighlightableLambdaExpression hle0 = new HighlightableLambdaExpression(zeroresult);
        assertEquals("0", hle0.toString());
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
        assertEquals("2", hle.toString());
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

        Term a = new Abstraction(new BoundVariable(1), "a");
        result1 = simple1.simplify(a);
        assertEquals(null, result1);

        Term c = new Abstraction(new Abstraction(new Abstraction(new FreeVariable("a"), "a"),
                "a"), "a");
        result1 = simple1.simplify(c);
        assertEquals(null, result1);

        Term d = new Abstraction(new Abstraction(new FreeVariable("a"), "a"), "a");
        result1 = simple1.simplify(d);
        assertEquals(null, result1);

        Term e = new Abstraction(new Abstraction(new ChurchNumber(1), "a"), "a");
        result1 = simple1.simplify(e);
        assertEquals(null, result1);
    }

    @Test
    public void isachruchwithfunction() {
        ChurchNumberSimplifier simpl = new ChurchNumberSimplifier();
        Term t = new Function("test", new ChurchNumber(0).getAbstraction());
        Term result = null;
        result = simpl.simplify(t);
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(result);
        assertEquals("0", hle.toString());

        Function b = new Function("b",new Abstraction(new BoundVariable(1),"a"));
        Term a = new Abstraction(b, "x");
        result = simpl.simplify(a);
        HighlightableLambdaExpression hle1 = new HighlightableLambdaExpression(result);
        assertEquals("0", hle1.toString());

        Function d = new Function("a", new BoundVariable(1));
        Term c = new Abstraction(new Abstraction(d,"a"),"x");
        result = simpl.simplify(c);
        HighlightableLambdaExpression hle2 = new HighlightableLambdaExpression(result);
        assertEquals("0", hle2.toString());
    }
}