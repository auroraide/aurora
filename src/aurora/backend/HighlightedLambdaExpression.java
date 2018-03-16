package aurora.backend;

import aurora.backend.parser.Token;

import java.util.Iterator;
import java.util.List;

/**
 * A read-only version of HighlightableLambdaExpression.
 */
public interface HighlightedLambdaExpression extends Iterable<Token> {
    @Override
    Iterator<Token> iterator();

    /**
     * Gets the redex resulting from the previous computation. Or null.
     *
     * @return Dings.
     */
    SubTerm getPreviousSubTerm();

    /**
     * Gets the redex that will be executed in the next step.
     *
     * @return
     */
    Redex getNextRedex();

    /**
     * Get all possible redexes.
     *
     * @return
     */
    List<Redex> getAllRedexes();

    class SubTerm {
        public final int startToken;
        public final int endToken;

        public SubTerm(int startToken, int endToken) {
            this.startToken = startToken;
            this.endToken = endToken;
        }
    }

    /**
     * Represents a redex in a specific tree. Information contained depends on that specific tree, and consists of
     * Token indices and a RedexPath.
     */
    class Redex extends SubTerm {
        /**
         * The first token of the right side of the application.
         */
        public final int middleToken;

        /**
         * Associated path to this redex in the specific tree this Redex belongs to.
         */
        public final RedexPath redex;

        /**
         * Creates a new Redex.
         *
         * @param startToken  Start.
         * @param middleToken Middle.
         * @param lastToken   End.
         * @param redex       Redex path.
         */
        public Redex(int startToken, int middleToken, int lastToken, RedexPath redex) {
            super(startToken, lastToken);
            this.middleToken = middleToken;
            this.redex = redex;
        }
    }
}
