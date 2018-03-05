package aurora.backend.parser;

import aurora.backend.TermPrinter;
import aurora.backend.library.HashLibrary;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import org.junit.Before;
import org.junit.Test;

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

}
