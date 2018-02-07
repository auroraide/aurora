package aurora.backend.parser;

import aurora.backend.TermVisitor;
import aurora.backend.library.Library;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LambdaParserTest {

    protected LambdaLexer lexer;

    protected LambdaParser parser;

    @Before
    public void setUp() {
        this.lexer = new LambdaLexer();
        this.parser = new LambdaParser(new Library());
    }

    @Test
    public void testParseEmptyString() {
        boolean thrown = false;
        try {
            this.parser.parse(this.lexer.lex("# lorem ipsum"));
        } catch (SyntaxException e) {
            thrown = true;
        } catch (SemanticException e) {
            fail("SemanticException thrown instead.");
        }

        assertTrue("No exception thrown.", thrown);
    }

    @Test
    public void testParseSomething() {
        /*TermVisitor<String> tv = new TermVisitor<String>() {
            @Override
            public String visit(Abstraction abs) {
                return "(\\" + abs.name + "." + abs.body.accept(this) + ")";
            }

            @Override
            public String visit(Application app) {
                return "[" + app.left.accept(this) + " " + app.right.accept(this) + "]";
            }

            @Override
            public String visit(BoundVariable bvar) {
                return "" + bvar.index;
            }

            @Override
            public String visit(FreeVariable fvar) {
                return fvar.name;
            }

            @Override
            public String visit(Function libterm) {
                return "$" + libterm.name;
            }

            @Override
            public String visit(ChurchNumber c) {
                return "c" + c.value;
            }
        };

        Term root = null;
        try {
            root = this.parser.parse(this.lexer.lex("(\\x . x x) (\\y . x y z)"));
        } catch (SyntaxException e) {
            e.printStackTrace();
        } catch (SemanticException e) {
            e.printStackTrace();
        }

        System.out.println(root.accept(tv));*/
    }

    @Test
    public void ichMachJetztAllesKaputt() {
        /*try {
            this.parser.parse(this.lexer.lex("\\x.\\y"));
        } catch (SyntaxException e) {
            e.printStackTrace();
        } catch (SemanticException e) {
            e.printStackTrace();
        }*/
    }

}
