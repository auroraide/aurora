package aurora.backend.parser;

import aurora.backend.parser.exceptions.SyntaxException;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

import java.util.LinkedList;
import java.util.List;

/**
 * Lexer for lambda expressions.
 */
public class LambdaLexer {

    private static final String T_LAMBDA_REGEX = "^\\\\";
    private static final String T_DOT_REGEX = "^\\.";
    private static final String T_VARIABLE_REGEX = "^([a-z]+)";
    private static final String T_LEFT_PARENS_REGEX = "^\\(";
    private static final String T_RIGHT_PARENS_REGEX = "^\\)";
    private static final String T_FUNCTION_REGEX = "^\\$([a-z]+)";
    private static final String T_NUMBER_REGEX = "^([0-9]+)";
    private static final String T_COMMENT_REGEX = "^\\#(\\V*)\\v?";
    private static final String T_WHITESPACE_REGEX = "^(\\s+)";

    private final RegExp lambdaPattern;
    private final RegExp dotPattern;
    private final RegExp variablePattern;
    private final RegExp leftParensPattern;
    private final RegExp rightParensPattern;
    private final RegExp functionPattern;
    private final RegExp numberPattern;
    private final RegExp commentPattern;
    private final RegExp whitespacePattern;

    /**
     * Creates a new (stateless) lexer to convert a String into a list of Tokens.
     */
    public LambdaLexer() {
        this.lambdaPattern = RegExp.compile(T_LAMBDA_REGEX);
        this.dotPattern = RegExp.compile(T_DOT_REGEX);
        this.variablePattern = RegExp.compile(T_VARIABLE_REGEX);
        this.leftParensPattern = RegExp.compile(T_LEFT_PARENS_REGEX);
        this.rightParensPattern = RegExp.compile(T_RIGHT_PARENS_REGEX);
        this.functionPattern = RegExp.compile(T_FUNCTION_REGEX);
        this.numberPattern = RegExp.compile(T_NUMBER_REGEX);
        this.commentPattern = RegExp.compile(T_COMMENT_REGEX);
        this.whitespacePattern = RegExp.compile(T_WHITESPACE_REGEX);
    }

    /**
     * Lex a lambda expression string into a list of {@link Token}s.
     *
     * @param code The input lambda expression string.
     * @return The corresponding {@link Token} stream if parsing was successful.
     * @throws SyntaxException In case of a syntax error.
     */
    public List<Token> lex(String code) throws SyntaxException {
        List<Token> result = new LinkedList<>();

        int line = 1;
        int column = 1;
        int offset = 0;

        while (!code.isEmpty()) {
            if (this.lambdaPattern.test(code)) {
                // capture regex group
                MatchResult match = this.lambdaPattern.exec(code);
                String whole = match.getGroup(0);

                // consume token
                code = code.substring(whole.length());

                // spawn token
                result.add(new Token(
                        Token.TokenType.T_LAMBDA,
                        line,
                        column,
                        offset
                ));

                // update line, column, and offset
                column += whole.length();
                ++offset;
            } else if (this.dotPattern.test(code)) {
                // capture regex group
                MatchResult match = this.dotPattern.exec(code);
                String whole = match.getGroup(0);

                // consume token
                code = code.substring(whole.length());

                // spawn token
                result.add(new Token(
                        Token.TokenType.T_DOT,
                        line,
                        column,
                        offset
                ));

                // update line, column, and offset
                column += whole.length();
                ++offset;
            } else if (this.variablePattern.test(code)) {
                // capture regex group
                MatchResult match = this.variablePattern.exec(code);
                String name = match.getGroup(1);
                String whole = match.getGroup(0);

                // consume token
                code = code.substring(whole.length());

                // spawn token
                result.add(new Token(
                        Token.TokenType.T_VARIABLE,
                        name,
                        line,
                        column,
                        offset
                ));

                // update line, column, and offset
                column += whole.length();
                ++offset;
            } else if (this.leftParensPattern.test(code)) {
                // capture regex group
                MatchResult match = this.leftParensPattern.exec(code);
                String whole = match.getGroup(0);

                // consume token
                code = code.substring(whole.length());

                // spawn token
                result.add(new Token(
                        Token.TokenType.T_LEFT_PARENS,
                        line,
                        column,
                        offset
                ));

                // update line, column, and offset
                column += whole.length();
                ++offset;
            } else if (this.rightParensPattern.test(code)) {
                // capture regex group
                MatchResult match = this.rightParensPattern.exec(code);
                String whole = match.getGroup(0);

                // consume token
                code = code.substring(whole.length());

                // spawn token
                result.add(new Token(
                        Token.TokenType.T_RIGHT_PARENS,
                        line,
                        column,
                        offset
                ));

                // update line, column, and offset
                column += whole.length();
                ++offset;
            } else if (this.functionPattern.test(code)) {
                // capture regex group
                MatchResult match = this.functionPattern.exec(code);
                String name = match.getGroup(1);
                String whole = match.getGroup(0);

                // consume token
                code = code.substring(whole.length());

                // spawn token
                result.add(new Token(
                        Token.TokenType.T_FUNCTION,
                        name,
                        line,
                        column,
                        offset
                ));

                // update line, column, and offset
                column += whole.length();
                ++offset;
            } else if (this.numberPattern.test(code)) {
                // capture regex group
                MatchResult match = this.numberPattern.exec(code);
                String name = match.getGroup(1);

                // consume token
                code = code.substring(name.length());
                // spawn token
                result.add(new Token(
                        Token.TokenType.T_NUMBER,
                        name,
                        line,
                        column,
                        offset
                ));

                // update line, column, and offset
                column += name.length();
                ++offset;
            } else if (this.commentPattern.test(code)) {
                // capture regex group
                MatchResult match = this.commentPattern.exec(code);
                String name = match.getGroup(1);
                String whole = match.getGroup(0);

                // consume token
                code = code.substring(whole.length());

                // spawn token
                result.add(new Token(
                        Token.TokenType.T_COMMENT,
                        name,
                        line,
                        column,
                        offset
                ));

                // update line, column, and offset
                ++line;
                column = 1;
                ++offset;
            } else if (this.whitespacePattern.test(code)) {
                // capture regex group
                MatchResult match = this.whitespacePattern.exec(code);
                String name = match.getGroup(1);

                // consume token
                code = code.substring(name.length());

                // spawn token
                result.add(new Token(
                        Token.TokenType.T_WHITESPACE,
                        name,
                        line,
                        column,
                        offset
                ));

                String[] whitelines = name.split("\r\n|\r|\n", -1);

                // update line, column, and offset
                if (whitelines.length > 1) {
                    // whitespace contains newlines
                    line += whitelines.length - 1;
                    column = whitelines[whitelines.length - 1].length() + 1;
                } else {
                    // whitespace is a single line
                    column += name.length();
                }
                ++offset;
            } else {
                throw new SyntaxException(
                        "Lex error at line " + line + ", column " + column + ".", line, column, offset);
            }
        }

        return result;
    }

}
