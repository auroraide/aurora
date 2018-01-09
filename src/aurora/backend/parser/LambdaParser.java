package aurora.backend.parser;

import aurora.backend.MetaTerm;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;

import java.util.List;

/**
 * Parser for lambda expressions.
 */
public class LambdaParser {

    /**
     * Parse a lambda expression string into a tree of {@link MetaTerm}s.
     *
     * Each {@link MetaTerm} keeps track of a reference to the original
     *
     * @param stream The {@link Token} stream constituting the lambda expression that shall be parsed.
     * @return The root node of the corresponding {@link MetaTerm} tree if parsing was successful.
     * @throws SyntaxException In case of a syntax error.
     * @throws SemanticException In case of a semantic error.
     */
    public MetaTerm parse(List<Token> stream) throws SyntaxException, SemanticException {
        return null;
    }

}
