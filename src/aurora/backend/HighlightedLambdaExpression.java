package aurora.backend;

import aurora.backend.parser.Token;
import aurora.backend.tree.Term;

import java.util.Iterator;
import java.util.List;

/**
 * A read-only version of HighlightableLambdaExpression.
 */
public interface HighlightedLambdaExpression extends Iterable<Token> {
    @Override
    Iterator<Token> iterator();

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

    /**
     * Represents a redex in a specific tree. Information contained depends on that specific tree, and consists of
     * Token indices and a RedexPath.
     */
    class Redex {
        /**
         * The very first token of the Redex. Usually a lambda.
         */
        public final int startToken;
        /**
         * The last token of the left side of the application.
         */
        public final int middleToken;
        /**
         * The last token of the right side of the application.
         */
        public final int lastToken;
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
            this.startToken = startToken;
            this.middleToken = middleToken;
            this.lastToken = lastToken;
            this.redex = redex;
        }
    }
}
