package aurora.backend.parser;

import aurora.backend.parser.exceptions.SyntaxException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TokenTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void herecomesthecov() {
        Token t = new Token(Token.TokenType.T_VARIABLE, "a", 0,0,0);
        Token f = new Token(Token.TokenType.T_VARIABLE, "a", 0, 0, 0);
        assertEquals(t.equals(f), true);
        Token x = t;
        assertEquals(t.equals(x), true);
        Token y = null;
        assertEquals(x.equals(y), false);
    }

    @Test
    public void comment() {
        LambdaLexer lexer = new LambdaLexer();
        String input = "#comm";
        List<Token> l = null;
        try {
            l = lexer.lex(input);
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
        Token t = l.get(0);

        int hash = t.hashCode();
        assertEquals(t.getType(), Token.TokenType.T_COMMENT);
        assertEquals(t.toString(), "#comm");

    }
}