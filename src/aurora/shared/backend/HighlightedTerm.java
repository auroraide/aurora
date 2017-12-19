package aurora.shared.backend;

/**
 * Encapsulates the lambda term combined with meta information about highlighting.
 */
public class HighlightedTerm {

    public HighlightedTerm() {}

    private int[] lambdas;

    private int[] dots;

    private int[] variables;

    private int[] functions;

    private int[] numbers;

    private Tuple<Integer, Integer>[] parenthesis;

    private Tuple<Integer, Integer>[] prevTerm;

    private Tuple<Integer, Integer>[] redexes;

    private int nextRedex;

    // getter
    // setter

}
