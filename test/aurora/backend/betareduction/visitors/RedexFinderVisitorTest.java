package aurora.backend.betareduction.visitors;

import aurora.backend.RedexPath;
import aurora.backend.library.HashLibrary;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.Term;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RedexFinderVisitorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findallredexes() {

        LambdaLexer lex = new LambdaLexer();
        LambdaParser parser = new LambdaParser(new HashLibrary());
        Term t = null;
        try {
            t = parser.parse(lex.lex("(\\x. x((\\y. y) y)) a"));
        } catch (SyntaxException e) {
            e.printStackTrace();
        } catch (SemanticException e) {
            e.printStackTrace();
        }
        RedexFinderVisitor rfv = new RedexFinderVisitor();
        t.accept(rfv);

        List<RedexPath> allredexes = rfv.getResult();
        RedexPath one = allredexes.get(0);
        RedexPath two = allredexes.get(1);
        LinkedList<RedexPath.Direction> onePath = one.getPath();
        LinkedList<RedexPath.Direction> twoPath = two.getPath();
        assertEquals("[]", onePath.toString());
        assertEquals("[LEFT, RIGHT]", twoPath.toString());
    }
}