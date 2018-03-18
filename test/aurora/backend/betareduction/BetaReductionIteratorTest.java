package aurora.backend.betareduction;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.RedexPath;
import aurora.backend.betareduction.strategies.CallByValue;
import aurora.backend.betareduction.strategies.NormalOrder;
import aurora.backend.library.HashLibrary;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Term;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;


public class BetaReductionIteratorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onebeta() {
        BetaReducer br = new BetaReducer(new NormalOrder());
        Term t = new Application(new Abstraction(new BoundVariable(1), "a"), new FreeVariable("x"));
        BetaReductionIterator it = new BetaReductionIterator(br, t);
        while (it.hasNext()) {
            t = it.next();
        }
        HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        assertEquals("x", hle.toString());
    }

    @Test
    public void nowhile() {
        boolean ex = false;
        BetaReducer br = new BetaReducer(new NormalOrder());
        Term t = new FreeVariable("x");
        BetaReductionIterator it = new BetaReductionIterator(br, t);

        try {
            it.next();
        } catch (NoSuchElementException e) {
            ex = true;
        }
        assertEquals(true, ex);
    }

    @Test
    public void path() {
        BetaReductionIterator br = new BetaReductionIterator(new BetaReducer(new NormalOrder()), new FreeVariable("a"));
        RedexPath path = br.getSelectedRedex();
        assertEquals(path, null);

    }
}