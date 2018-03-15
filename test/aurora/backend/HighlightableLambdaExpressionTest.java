package aurora.backend;

import aurora.backend.betareduction.BetaReducer;
import aurora.backend.betareduction.strategies.NormalOrder;
import aurora.backend.library.HashLibrary;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
        assertEquals("x",hle.toString());

    }

    @Test
    public void withparenscauseapp() {
        Abstraction t = new ChurchNumber(2).getAbstraction();
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("\\s. \\z. s (s z)", hle.toString());

    }

    @Test
    public void withparenscauseabs() {
        Term t = new Application(
                new Abstraction(new BoundVariable(1),"x"), new FreeVariable("y")
        );
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("(\\x. x) y",hle.toString());
    }

    @Test
    public void doublealpha() {
        Term t = new Abstraction(
                new Abstraction(
                        new BoundVariable(1),"x"
                ),"x"
        );

        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("\\x. \\x1. x1", hle.toString());
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
        assertEquals("\\x. \\x1. \\x2. x2",hle.toString());
    }

    @Test
    public void triplealphawithBound() {
        Term t = new Abstraction(
                new Abstraction(
                        new Abstraction(
                                new BoundVariable(2),"x"
                        ),"x"
                ),"x"
        );
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("\\x. \\x1. \\x2. x1",hle.toString());
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
        assertEquals("\\x_alpha. \\x1. x1 x", hle.toString());
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
        assertEquals("(\\x. x x) (\\x. x x)",hle.toString());

    }

    @Test
    public void morealpha() {
        Term t = new Abstraction(
                new Application(
                        new BoundVariable(1), new FreeVariable("x")
                ),"x"
        );
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("\\x_alpha. x_alpha x",hle.toString());
    }

    @Test
    public void alphaconvnewchurch() {
        LambdaLexer lexer = new LambdaLexer();
        LambdaParser parser = new LambdaParser(new HashLibrary());
        String input = "2 2";
        Term t = null;
        try {
            t = parser.parse(lexer.lex(input));
        } catch (SyntaxException e) {
            e.printStackTrace();
        } catch (SemanticException e) {
            e.printStackTrace();
        }

        BetaReducer br = new BetaReducer(new NormalOrder());
        for (int n = 0; n < 6; n++) {
            t = br.reduce(t);

        }
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);

        assertEquals("\\z. \\z1. z (z (z (z z1)))", hle.toString());

    }

    /**
     * the function and chruchnumbers dont get changed so print the original name/number.
     */
    @Test
    public void dontChangeNames() {
        Term t = new ChurchNumber(1);
        HighlightableLambdaExpression hle1 = new HighlightableLambdaExpression(t);
        assertEquals("1",hle1.toString());

        Term x = new Function("name", new FreeVariable("x"));
        HighlightableLambdaExpression hle2 = new HighlightableLambdaExpression(x);
        assertEquals("$name", hle2.toString());
    }

    @Test
    public void double_alphaEasiy() {
        Term t = new Abstraction(new FreeVariable("x_alpha"), "x_alpha");
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("\\x_alpha_alpha. x_alpha", hle.toString());
    }

    @Test
    public void x1_fvar() {
        Term t = new Abstraction(
                new Abstraction(
                        new FreeVariable("x1"), "x"
                ), "x"
        );
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("\\x. \\x1_alpha. x1", hle.toString());
    }

    @Test
    public void double_alphaHard() {
        Term t = new Abstraction(
                new Application(
                        new FreeVariable("x"), new FreeVariable("x_alpha")
                ), "x"
        );
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("\\x_alpha_alpha. x x_alpha", hle.toString());

        Term f = new Abstraction(
                new Application(
                        new Application(
                                new FreeVariable("x"), new FreeVariable("x_alpha")
                        ), new FreeVariable("x_alpha_alpha")
                ), "x"
        );

        HighlightableLambdaExpression hle3 = new HighlightableLambdaExpression(f);
        assertEquals("\\x_alpha_alpha_alpha. x x_alpha x_alpha_alpha", hle3.toString());
    }


}
