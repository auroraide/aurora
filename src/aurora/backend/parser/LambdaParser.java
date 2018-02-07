package aurora.backend.parser;

import aurora.backend.MetaTerm;
import aurora.backend.library.Library;
import aurora.backend.library.exceptions.LibraryItemNotFoundException;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;

import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;

import java.util.List;
import java.util.Stack;

/**
 * Impure recursive descent parser for lambda expressions from {@link Token} streams.
 */
public class LambdaParser {

    private Stack<Token> inputStack;

    private Stack<String> variableStack;

    private Library library;

    /**
     * Construct a new parser that checks for the existence of function names within the given {@link Library}.
     */
    public LambdaParser(Library library) {
        this.library = library;
        this.inputStack = new Stack<>();
        this.variableStack = new Stack<>();
    }

    /**
     * Parse a {@link Token} stream into a tree of {@link MetaTerm}s.
     * <p>
     * Each {@link MetaTerm} keeps track of a reference to the original {@link Token}.
     *
     * @param stream The {@link Token} stream constituting the lambda expression that shall be parsed.
     * @return The root node of the corresponding {@link MetaTerm} tree if parsing was successful.
     * @throws SyntaxException   In case of a syntax error.
     * @throws SemanticException In case of a semantic error like using an undefined function.
     */
    public MetaTerm parse(List<Token> stream) throws SyntaxException, SemanticException {
        // the grammar would look something like this
        //
        // expr --> term | term expr
        // term --> LEFT_PARENS expr RIGHT_PARENS | LAMBDA VARIABLE DOT expr | VARIABLE | FUNCTION | NUMBER
        //

        // push tokens on stack in reverse order and discard whitespaces and comments
        for (int i = stream.size() - 1; i >= 0; --i) {
            Token t = stream.get(i);
            if (t.getType() != Token.TokenType.T_WHITESPACE
                    && t.getType() != Token.TokenType.T_COMMENT) {
                this.inputStack.push(t);
            }
        }

        if (this.inputStack.isEmpty()) {
            throw new SyntaxException("Parse error: input stream is empty.");
        }

        MetaTerm result = this.expr();

        if (!this.inputStack.isEmpty()) {
            throw new SyntaxException(
                    "Parse error at line "
                    + this.inputStack.peek().getLine()
                    + ", column "
                    + this.inputStack.peek().getColumn()
                    + ": unexpected "
                    + this.inputStack.peek().getType()
                    + " found.",
                    this.inputStack.peek().getLine(),
                    this.inputStack.peek().getColumn(),
                    this.inputStack.peek().getOffset());
        }

        return result;
    }

    private MetaTerm expr() throws SyntaxException, SemanticException {
        MetaTerm left = this.term();

        // parse term* as long as possible
        while (!this.inputStack.isEmpty()
                && (this.inputStack.peek().getType() == Token.TokenType.T_LEFT_PARENS
                || this.inputStack.peek().getType() == Token.TokenType.T_LAMBDA
                || this.inputStack.peek().getType() == Token.TokenType.T_VARIABLE
                || this.inputStack.peek().getType() == Token.TokenType.T_FUNCTION
                || this.inputStack.peek().getType() == Token.TokenType.T_NUMBER)) {

            left = new MetaTerm(new Application(left, this.term()), left.token);

        }

        return left;
    }

    private MetaTerm term() throws SyntaxException, SemanticException {
        if (this.inputStack.peek().getType()
                == Token.TokenType.T_LEFT_PARENS) {

            this.inputStack.pop();
            MetaTerm expr = this.expr();
            this.expect(Token.TokenType.T_RIGHT_PARENS);
            return expr;

        } else if (this.inputStack.peek().getType()
                == Token.TokenType.T_LAMBDA) {

            Token lambda = this.inputStack.pop();
            String varName = this.expect(Token.TokenType.T_VARIABLE).getName();
            this.expect(Token.TokenType.T_DOT);

            // push name of potential bound variables on stack
            this.variableStack.push(varName);

            MetaTerm result = new MetaTerm(new Abstraction(this.expr(), varName), lambda);

            this.variableStack.pop();

            return result;

        } else if (this.inputStack.peek().getType()
                == Token.TokenType.T_VARIABLE) {

            Token var = this.inputStack.pop();

            // bound or free variable
            int idx = this.variableStack.search(var.getName());
            if (idx > 0) {
                return new MetaTerm(new BoundVariable(idx), var);
            }
            return new MetaTerm(new FreeVariable(var.getName()), var);

        } else if (this.inputStack.peek().getType()
                == Token.TokenType.T_FUNCTION) {

            // check function name is defined in library
            if (!this.library.exists(this.inputStack.peek().getName())) {
                throw new SemanticException("Parse error at line "
                        + this.inputStack.peek().getLine()
                        + ", column "
                        + this.inputStack.peek().getColumn()
                        + ": function "
                        + this.inputStack.peek().toString()
                        + " is undefined.",
                        this.inputStack.peek().getLine(),
                        this.inputStack.peek().getColumn(),
                        this.inputStack.peek().getOffset());
            }

            try {

                return new MetaTerm(new Function(
                        this.inputStack.peek().getName(),
                        this.library.getItem(this.inputStack.peek().getName()).getTerm()),
                        this.inputStack.pop());

            } catch (LibraryItemNotFoundException e) {
                // should never happen as we already checked that the item exists
                throw new RuntimeException(e.getClass().getCanonicalName() + ": " + e.getMessage());
            }

        } else if (this.inputStack.peek().getType()
                == Token.TokenType.T_NUMBER) {

            return new MetaTerm(new ChurchNumber(
                    Integer.parseInt(this.inputStack.peek().getName())),
                    this.inputStack.pop());

        }

        throw new SyntaxException(
                "Parse error at line "
                + this.inputStack.peek().getLine()
                + ", column "
                + this.inputStack.peek().getColumn()
                + ": unexpected "
                + this.inputStack.peek().getType()
                + " found.",
                this.inputStack.peek().getLine(),
                this.inputStack.peek().getColumn(),
                this.inputStack.peek().getOffset());
    }

    private Token expect(Token.TokenType type) throws SyntaxException {
        if (this.inputStack.isEmpty()) {
            throw new SyntaxException(
                    "Parse error: expecting "
                    + type
                    + ", but end of input stream found.");
        }

        if (this.inputStack.peek().getType() == type) {
            return this.inputStack.pop();
        }

        throw new SyntaxException(
                "Parse error at line "
                + this.inputStack.peek().getLine()
                + ", column "
                + this.inputStack.peek().getColumn()
                + ": expected "
                + type
                + ", but "
                + this.inputStack.peek().getType()
                + " found.",
                this.inputStack.peek().getLine(),
                this.inputStack.peek().getColumn(),
                this.inputStack.peek().getOffset());
    }

}
