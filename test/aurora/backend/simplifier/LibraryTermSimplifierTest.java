package aurora.backend.simplifier;

import aurora.backend.TermPrinter;
import aurora.backend.library.HashLibrary;
import aurora.backend.library.Library;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class LibraryTermSimplifierTest {

    @Test
    public void testSimplify() {
        Library library = new HashLibrary();
        LambdaLexer lexer = new LambdaLexer();
        LambdaParser parser = new LambdaParser(library);

        try {

            library.define("foo", "", parser.parse(lexer.lex("\\x. x x")));
            library.define("bar", "", parser.parse(lexer.lex("\\x. \\y. y x")));

        } catch (SyntaxException e) {
            e.printStackTrace();
            fail("SyntaxException thrown instead: " + e.getMessage());
        } catch (SemanticException e) {
            e.printStackTrace();
            fail("SemanticException thrown instead: " + e.getMessage());
        }

        ResultSimplifier<Function> simplifier = new LibraryTermSimplifier(library);

        try {
            Term t = simplifier.simplify(parser.parse(lexer.lex("\\z. z z")));
            assertNotNull(t);

            assertEquals(
                    "$foo",
                    t.accept(new TermPrinter()));
        } catch (SyntaxException e) {
            e.printStackTrace();
            fail("SyntaxException thrown instead: " + e.getMessage());
        } catch (SemanticException e) {
            e.printStackTrace();
            fail("SemanticException thrown instead: " + e.getMessage());
        }
    }

    @Test
    public void specialfunctions() {
        Library lib = new HashLibrary();
        lib.define("f", "f", new Function(
                "f", (new Function(("a"), new FreeVariable("a")))));
        LibraryTermSimplifier lts = new LibraryTermSimplifier(lib);

        Function f = lts.simplify(new Function("f", new FreeVariable("a")));

        assertEquals(f.name, "f");
        Function zero = lts.simplify(new FreeVariable("b"));
        assertEquals(null, zero);

        lib.define("c", "c", new ChurchNumber(2));
        Function church = lts.simplify(new ChurchNumber(2));
        assertEquals(church.name, "c");

        lib.define("fvar", "fvar", new FreeVariable("y"));
        Function fv = lts.simplify(new FreeVariable("y"));
        assertEquals("fvar", fv.name);

        Function fverror = lts.simplify(new FreeVariable("z"));
        assertEquals(fverror, null);
    }

}