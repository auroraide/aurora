package aurora.backend.parser;

import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.Term;

import java.util.List;

/**
 * Lexer for lambda expressions.
 */
public class LambdaLexer {

    /**
     * Lex a lambda expression string into a list of {@link Token}s.
     *
     * @param code The input lambda expression string.
     * @return The corresponding {@link Token} stream if parsing was successful.
     * @throws SyntaxException In case of a syntax error.
     */
    public List<Token> lex(String code) throws SyntaxException {
        return null;
    }

}
