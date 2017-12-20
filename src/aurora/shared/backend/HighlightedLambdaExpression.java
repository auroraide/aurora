package aurora.shared.backend;

/**
 * Encapsulates the lambda term combined with meta information about highlighting.
 */
public class HighlightedLambdaExpression {

    private int[] lambdas;

    private int[] dots;

    private int[] variables;

    private int[] functions;

    private int[] numbers;

    private Tuple<Integer, Integer>[] parenthesis;

    private Tuple<Integer, Integer>[] prevTerm;

    private Tuple<Integer, Integer>[] redexes;

    private int nextRedex;

    public HighlightedLambdaExpression(
            int[] lambdas,
            int[] dots,
            int[] variables,
            int[] functions,
            int[] numbers,
            Tuple<Integer, Integer>[] parenthesis,
            Tuple<Integer, Integer>[] prevTerm,
            Tuple<Integer, Integer>[] redexes,
            int nextRedex) {

    }

    public int[] getLambdas() {}

    public int[] getDots() {}

    public int[] getVariables() {}

    public int[] getFunctions() {}

    public int[] getNumbers() {}

    public Tuple<Integer, Integer>[] getParenthesis() {}

    public Tuple<Integer, Integer>[] getPrevTerm() {}

    public Tuple<Integer, Integer>[] getRedexes() {}

    public int getNextRedex() {}

}
