package aurora.backend.parser;

import aurora.backend.TermPrinter;
import aurora.backend.library.HashLibrary;
import aurora.backend.library.Library;
import aurora.backend.library.LibraryItem;
import aurora.backend.library.exceptions.LibraryItemNotFoundException;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.FreeVariable;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LambdaParserTest {

    private LambdaLexer lexer;

    private LambdaParser parser;

    @Before
    public void setUp() {
        this.lexer = new LambdaLexer();
        this.parser = new LambdaParser(new HashLibrary());
    }

    @Test
    public void testParseEmptyString() {
        boolean thrown = false;
        try {
            this.parser.parse(this.lexer.lex("# lorem ipsum"));
        } catch (SyntaxException e) {
            thrown = true;
        } catch (SemanticException e) {
            e.printStackTrace();
            fail("SemanticException thrown instead: " + e.getMessage());
        }

        assertTrue("No exception thrown.", thrown);
    }

    @Test
    public void testParseReallyEmptyString() {
        boolean thrown = false;
        try {
            this.parser.parse(this.lexer.lex(""));
        } catch (SyntaxException e) {
            thrown = true;
        } catch (SemanticException e) {
            e.printStackTrace();
            fail("SemanticException thrown instead: " + e.getMessage());
        }

        assertTrue("No exception thrown.", thrown);
    }

    @Test
    public void testNikosExpressions() {
        String[] input = {
                "((\\x.(x x)) (\\y.((x y) z)))",
                "\\x.\\y.(\\z.x y z) x y z"
        };

        String[] expected = {
                "((\\x.(1 1)) (\\y.((x 1) z)))",
                "(\\x.(\\y.((((\\z.((3 2) 1)) 2) 1) z)))"
        };

        this.assertParse(input, expected);


    }


    @Test
    public void firstLineComment() {
        String[] input = {
            "#Comment\n((\\x.(x x)) (\\y.((x y) z)))"
        };
        String[] expected = {
            "((\\x.(1 1)) (\\y.((x 1) z)))",
        };

        this.assertParse(input, expected);
    }

    private void assertParse(String[] input, String[] expected) {
        for (int i = 0; i < expected.length; ++i) {
            try {

                assertEquals("Testing input[" + i + "]",
                        expected[i],
                        this.parser.parse(this.lexer.lex(input[i])).accept(new TermPrinter()));

            } catch (SyntaxException e) {
                e.printStackTrace();
                fail("SyntaxException thrown instead: " + e.getMessage());
            } catch (SemanticException e) {
                e.printStackTrace();
                fail("SemanticException thrown instead: " + e.getMessage());
            }
        }
    }

    @Test
    public void testNumbers() {
        String[] input = {
                "(\\x. x x) 42"
        };

        String[] expected = {
                "((\\x.(1 1)) c42)"
        };

        this.assertParse(input, expected);
    }

    @Test
    public void testNumberThatIsReallyHuge() {
        String input = "(\\x. x x) 9999999999999999999999999999999999999999999999999999999999999999999999999999";
        boolean thrown = false;

        try {
            this.parser.parse(this.lexer.lex(input));
        } catch (SyntaxException e) {
            e.printStackTrace();
            fail("SyntaxException thrown instead: " + e.getMessage());
        } catch (SemanticException e) {
            thrown = true;
        }

        assertTrue("No exception thrown.", thrown);
    }

    @Test
    public void semex() {
        String input = "$thisisnotdefined";
        boolean thrown = false;

        try {
            this.parser.parse(this.lexer.lex(input));
        } catch (SemanticException e) {
            thrown = true;
            assertEquals(e.getColumn(), 1);
            assertEquals(0, e.getOffset());
            assertEquals(1, e.getLine());
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
        assertEquals(thrown, true);
    }

    @Test
    public void exceptions() {
        String input = "\\\\";
        boolean thrown = false;
        try {
            this.parser.parse(this.lexer.lex(input));
        } catch (SemanticException e) {
            e.printStackTrace();
        } catch (SyntaxException e) {
            thrown = true;

        }
        assertEquals(thrown, true);
    }

    // coverage for a broken library
    @Test
    public void funcs() {

        class Stupidlib implements Library {

            @Override
            public LibraryItem getItem(String name) throws LibraryItemNotFoundException {
                throw new LibraryItemNotFoundException();
            }

            @Override
            public void define(LibraryItem item) {

            }

            @Override
            public void define(Library library) {

            }

            @Override
            public void remove(String name) {

            }

            @Override
            public boolean exists(String name) {
                return true;
            }

            @Override
            public Iterator<LibraryItem> iterator() {
                return null;
            }
        }

        Stupidlib lib = new Stupidlib();
        lib.define("test","t", new FreeVariable("a"));
        LambdaParser pars2 = new LambdaParser(lib);
        boolean exp = false;
        try {
            pars2.parse(this.lexer.lex("$err"));
        } catch (SyntaxException e) {
            e.printStackTrace();
        } catch (SemanticException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            exp = true;
        }
        assertEquals(true, exp);
    }


    @Test
    public void expect() {
        String input = ("\\");
        boolean ex = false;
        try {
            this.parser.parse(this.lexer.lex(input));
        } catch (SemanticException e) {
            e.printStackTrace();
        } catch (SyntaxException e) {
            ex = true;
        }
        assertEquals(true, ex);
    }

    @Test
    public void heregoesnothing() {
        SemanticException s = new SemanticException();
        assertEquals(s.getMessage(), "");
        SemanticException s1 = new SemanticException("itsbroke");
        assertEquals(s1.getMessage(), "itsbroke");
        SyntaxException s2 = new SyntaxException();
        assertEquals(s2.getMessage(), "");
    }

}
