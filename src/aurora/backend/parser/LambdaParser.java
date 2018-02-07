package aurora.backend.parser;

import aurora.backend.MetaTerm;
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

import java.util.List;
import java.util.Stack;

/**
 * Parser for lambda expressions.
 */
public class LambdaParser {

    private Stack<Token> stack;

    private int rightParens;

    private Library library;

    /**
     * Constructor.
     */
    public LambdaParser(Library library) {
        this.library = library;
        this.stack = new Stack<>();
        this.rightParens = 0;
    }

    /**
     * Parse a lambda expression string into a tree of {@link MetaTerm}s.
     * <p>
     * Each {@link MetaTerm} keeps track of a reference to the original {@link Token}.
     *
     * @param stream The {@link Token} stream constituting the lambda expression that shall be parsed.
     * @return The root node of the corresponding {@link MetaTerm} tree if parsing was successful.
     * @throws SyntaxException   In case of a syntax error.
     * @throws SemanticException In case of a semantic error.
     */
    public MetaTerm parse(List<Token> stream) throws SyntaxException, SemanticException {
        // expr --> term | term expr
        // term --> ( expr ) | \ var . expr | var | func | num

        // push tokens on stack in reverse order and discard whitespaces and comments
        for (int i = stream.size() - 1; i >= 0; --i) {
            Token t = stream.get(i);
            if (t.getType() != Token.TokenType.T_WHITESPACE
                    && t.getType() != Token.TokenType.T_COMMENT) {
                this.stack.push(t);
            }
        }

        if (this.stack.isEmpty()) {
            throw new SyntaxException("Parse error: input is empty.");
        }

        return this.expr();
    }

    private MetaTerm expr() throws SyntaxException, SemanticException {
        MetaTerm left = this.term();

        if ((this.stack.isEmpty()
                && this.rightParens == 0)
                || (!this.stack.isEmpty()
                && this.stack.peek().getType() == Token.TokenType.T_RIGHT_PARENS
                && this.rightParens != 0)) {

            return left;

        }

        if (!this.stack.isEmpty()) {

            return new MetaTerm(new Application(left, this.expr()), left.token);

        }

        throw new SyntaxException(
                "Parse error at line "
                + left.token.getLine()
                + ", column "
                + left.token.getColumn()
                + ": unexpected end of input stream after "
                + left.token.getType()
                + ".",
                left.token.getLine(),
                left.token.getColumn(),
                left.token.getOffset());
    }

    private MetaTerm term() throws SyntaxException, SemanticException {
        if (this.stack.peek().getType()
                == Token.TokenType.T_LEFT_PARENS) {

            this.stack.pop();
            ++this.rightParens;
            MetaTerm expr = this.expr();
            this.expect(Token.TokenType.T_RIGHT_PARENS);
            --this.rightParens;
            return expr;

        } else if (this.stack.peek().getType()
                == Token.TokenType.T_LAMBDA) {

            Token lambda = this.stack.pop();
            String varName = this.expect(Token.TokenType.T_VARIABLE).getName();
            this.expect(Token.TokenType.T_DOT);
            return new MetaTerm(new Abstraction(this.term(), varName), lambda);

        } else if (this.stack.peek().getType()
                == Token.TokenType.T_VARIABLE) {

            // TODO free vs bound variable

            return new MetaTerm(new FreeVariable(this.stack.peek().getName()), this.stack.pop());

        } else if (this.stack.peek().getType()
                == Token.TokenType.T_FUNCTION) {

            // TODO function exists check
            if (false) {
                throw new SemanticException();
            }

            return new MetaTerm(new Function(this.stack.peek().getName(), null), this.stack.pop());

        } else if (this.stack.peek().getType()
                == Token.TokenType.T_NUMBER) {

            return new MetaTerm(new ChurchNumber(Integer.parseInt(this.stack.peek().getName())), this.stack.pop());

        }

        throw new SyntaxException(
                "Parse error at line "
                + this.stack.peek().getLine()
                + ", column "
                + this.stack.peek().getColumn()
                + ": unexpected "
                + this.stack.peek().getType()
                + " found.",
                this.stack.peek().getLine(),
                this.stack.peek().getColumn(),
                this.stack.peek().getOffset());
    }

    private Token expect(Token.TokenType type) throws SyntaxException {
        if (this.stack.isEmpty()) {
            throw new SyntaxException(
                    "Parse error: expecting "
                    + type
                    + ", but end of input stream found.");
        }

        if (this.stack.peek().getType() == type) {
            return this.stack.pop();
        }

        throw new SyntaxException(
                "Parse error at line "
                + this.stack.peek().getLine()
                + ", column "
                + this.stack.peek().getColumn()
                + ": expected "
                + type
                + ", but "
                + this.stack.peek().getType()
                + " found.",
                this.stack.peek().getLine(),
                this.stack.peek().getColumn(),
                this.stack.peek().getOffset());
    }

}
