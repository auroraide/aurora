package aurora.shared.backend.parser;

import aurora.shared.backend.parser.exceptions.SemanticException;
import aurora.shared.backend.parser.exceptions.SyntaxException;
import aurora.shared.backend.tree.Term;

/**
 * Parser for lambda expressions.
 */
public class LambdaParser {

    /**
     * Parse a lambda expression string into a tree of Terms.
     *
     * @param code The lambda expression as a string.
     * @return The root node of the corresponding Term tree if parsing was successful.
     * @throws SyntaxException In case of a syntax error.
     * @throws SemanticException In case of a semantic error.
     */
    public Term parse(String code) throws SyntaxException, SemanticException {
        return null;
    }

}
