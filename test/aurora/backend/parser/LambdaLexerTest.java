package aurora.backend.parser;

import aurora.backend.parser.exceptions.SyntaxException;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests LambdaLexer.
 */
public class LambdaLexerTest {
    @Test
    public void testLexValidExpression() throws SyntaxException {
        LambdaLexer lexer = new LambdaLexer();
        List<Token> actual = lexer.lex("(\\x.x foo)((\\y.y bar)\n    ($add 32 42  )) # blah blah blah");
        List<Token> expected = Arrays.asList(
                new Token(Token.TokenType.T_LEFT_PARENS, 1, 1, 0),
                new Token(Token.TokenType.T_LAMBDA, 1, 2, 1),
                new Token(Token.TokenType.T_VARIABLE, "x", 1, 3, 2),
                new Token(Token.TokenType.T_DOT, 1, 4, 3),
                new Token(Token.TokenType.T_VARIABLE, "x", 1, 5, 4),
                new Token(Token.TokenType.T_WHITESPACE, " ", 1, 6, 5),
                new Token(Token.TokenType.T_VARIABLE, "foo", 1, 7, 6),
                new Token(Token.TokenType.T_RIGHT_PARENS, 1, 10, 7),
                new Token(Token.TokenType.T_LEFT_PARENS, 1, 11, 8),
                new Token(Token.TokenType.T_LEFT_PARENS, 1, 12, 9),
                new Token(Token.TokenType.T_LAMBDA, 1, 13, 10),
                new Token(Token.TokenType.T_VARIABLE, "y", 1, 14, 11),
                new Token(Token.TokenType.T_DOT, 1, 15, 12),
                new Token(Token.TokenType.T_VARIABLE, "y", 1, 16, 13),
                new Token(Token.TokenType.T_WHITESPACE, " ", 1, 17, 14),
                new Token(Token.TokenType.T_VARIABLE, "bar", 1, 18, 15),
                new Token(Token.TokenType.T_RIGHT_PARENS, 1, 21, 16),
                new Token(Token.TokenType.T_WHITESPACE, "\n    ", 1, 22, 17),
                new Token(Token.TokenType.T_LEFT_PARENS, 2, 5, 18),
                new Token(Token.TokenType.T_FUNCTION, "add", 2, 6, 19),
                new Token(Token.TokenType.T_WHITESPACE, " ", 2, 10, 20),
                new Token(Token.TokenType.T_NUMBER, "32", 2, 11, 21),
                new Token(Token.TokenType.T_WHITESPACE, " ", 2, 13, 22),
                new Token(Token.TokenType.T_NUMBER, "42", 2, 14, 23),
                new Token(Token.TokenType.T_WHITESPACE, "  ", 2, 16, 24),
                new Token(Token.TokenType.T_RIGHT_PARENS, 2, 18, 25),
                new Token(Token.TokenType.T_RIGHT_PARENS, 2, 19, 26),
                new Token(Token.TokenType.T_WHITESPACE, " ", 2, 20, 27),
                new Token(Token.TokenType.T_COMMENT, " blah blah blah", 2, 21, 28)
        );

        /*System.out.println("\nactual = ");
        for (Token t : actual) {
            System.out.print(t.debug());
        }
        System.out.println("\nexpected = ");
        for (Token t : expected) {
            System.out.print(t.debug());
        }
        System.out.println();*/

        assertThat(actual, is(expected));
    }

    @Test
    public void testFunctionNames() throws SyntaxException {
        LambdaLexer lexer = new LambdaLexer();
        List<Token> actual = lexer.lex("$Func42 $Foo_Bar_42 $foo1337__baR");
        List<Token> expected = Arrays.asList(
                new Token(Token.TokenType.T_FUNCTION,
                        "Func42",
                        1, 1, 0),
                new Token(Token.TokenType.T_WHITESPACE,
                        " ",
                        1, 8, 1),
                new Token(Token.TokenType.T_FUNCTION,
                        "Foo_Bar_42",
                        1, 9, 2),
                new Token(Token.TokenType.T_WHITESPACE,
                        " ",
                        1, 20, 3),
                new Token(Token.TokenType.T_FUNCTION,
                        "foo1337__baR",
                        1, 21, 4)
        );

        assertThat(actual, is(expected));
    }

    @Test
    public void testVariableNames() throws SyntaxException {
        LambdaLexer lexer = new LambdaLexer();
        List<Token> actual = lexer.lex("Func42 Foo_Bar_42 foo1337__baR");
        List<Token> expected = Arrays.asList(
                new Token(Token.TokenType.T_VARIABLE,
                        "Func42",
                        1, 1, 0),
                new Token(Token.TokenType.T_WHITESPACE,
                        " ",
                        1, 7, 1),
                new Token(Token.TokenType.T_VARIABLE,
                        "Foo_Bar_42",
                        1, 8, 2),
                new Token(Token.TokenType.T_WHITESPACE,
                        " ",
                        1, 18, 3),
                new Token(Token.TokenType.T_VARIABLE,
                        "foo1337__baR",
                        1, 19, 4)
        );

        assertThat(actual, is(expected));
    }

    @Test
    public void testComments() throws SyntaxException {
        LambdaLexer lexer = new LambdaLexer();

        List<Token> actual = lexer.lex("4 4 # foo");
        List<Token> expected = Arrays.asList(
                new Token(Token.TokenType.T_NUMBER,
                        "4",
                        1, 1, 0),
                new Token(Token.TokenType.T_WHITESPACE,
                        " ",
                        1, 2, 1),
                new Token(Token.TokenType.T_NUMBER,
                        "4",
                        1, 3, 2),
                new Token(Token.TokenType.T_WHITESPACE,
                        " ",
                        1, 4, 3),
                new Token(Token.TokenType.T_COMMENT,
                        " foo",
                        1, 5, 4)
        );

        assertThat(actual, is(expected));
    }

    @Test
    public void testCommentFollowedByNewline() throws SyntaxException {
        LambdaLexer lexer = new LambdaLexer();

        List<Token> actual;
        List<Token> expected = Arrays.asList(
                new Token(Token.TokenType.T_COMMENT,
                        " foo",
                        1, 1, 0),
                new Token(Token.TokenType.T_LAMBDA,
                        2, 1, 1),
                new Token(Token.TokenType.T_VARIABLE,
                        "x",
                        2, 2, 2),
                new Token(Token.TokenType.T_DOT,
                        2, 3, 3),
                new Token(Token.TokenType.T_VARIABLE,
                        "x",
                        2, 4, 4)
        );

        // LF
        actual = lexer.lex("# foo\n\\x.x");
        assertThat(actual, is(expected));

        // CR LF
        actual = lexer.lex("# foo\r\n\\x.x");
        assertThat(actual, is(expected));
    }

    @Test
    public void testLexInvalidExpression() {
        LambdaLexer lexer = new LambdaLexer();
        boolean thrown = false;
        try {
            lexer.lex("(\\x. x x)\n($$add 4 2)");
        } catch (SyntaxException e) {
            thrown = true;
            assertEquals(2, e.getLine());
            assertEquals(2, e.getColumn());
            assertEquals(11, e.getOffset());
        }
        assertTrue("lex() should have thrown a SyntaxException", thrown);
    }

    @Test
    public void testLexInvalidExpressionJp() {
        LambdaLexer lexer = new LambdaLexer();
        boolean thrown = false;
        try {
            lexer.lex("(\\光.光 水) 火");
        } catch (SyntaxException e) {
            thrown = true;
            assertEquals(1, e.getLine());
        }
        assertTrue("lex() should have thrown a SyntaxException", thrown);
    }

}
