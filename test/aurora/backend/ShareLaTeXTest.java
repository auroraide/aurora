package aurora.backend;

import aurora.backend.library.HashLibrary;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.Term;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShareLaTeXTest {
    @Test
    public void testManyTerms() throws SyntaxException, SemanticException {
        final String[] originals = {
                "$true (a) 578",
                "($true $true $true ($true) 2 2 2 ($true 1) \\x.($true x \\x.x)) 3"
        };
        final String[] expected = {
                "$(\\texttt{\\$true})\\ a\\ (\\textbf{578})$",
                "$(\\texttt{\\$true})\\ (\\texttt{\\$true})\\ (\\texttt{\\$true})\\ (\\texttt{\\$true})\\ "
                        + "(\\textbf{2})\\ (\\textbf{2})\\ (\\textbf{2})\\ ((\\texttt{\\$true})\\ (\\textbf{1}))\\ "
                        + "(\\lambda x.\\ (\\texttt{\\$true})\\ x\\ (\\lambda x1.\\ x1))\\ (\\textbf{3})$"
        };

        HashLibrary library = new HashLibrary();
        LambdaLexer lexer = new LambdaLexer();
        LambdaParser parser = new LambdaParser(library);
        library.define("true", "", parser.parse(lexer.lex("\\t.\\f.t")));
        for (int i = 0; i < originals.length; i++) {
            Term t = parser.parse(lexer.lex(originals[i]));
            ShareLaTeX shareLaTeX = new ShareLaTeX(new HighlightableLambdaExpression(t));
            String actual = shareLaTeX.generateLaTeX();
            assertEquals(expected[i], actual);
        }
    }
}