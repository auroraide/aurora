package aurora.backend.parser;

import aurora.backend.parser.exceptions.SyntaxException;
import junit.framework.TestCase;

import java.util.List;

public class LambdaLexerTest extends TestCase {

    public void testLex() {
        LambdaLexer lexer = new LambdaLexer();
        try {
            List<Token> stream = lexer.lex("\\x.x y z ((\\f . f g h)\n($add 32 42) )");
            for (Token t : stream) {
                System.out.print(t.toString());
            }
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
    }

}
