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

    public int[] getLambdas() {
        return this.lambdas;
    }

    public int[] getDots() {
        return this.dots;
    }

    public int[] getVariables() {
        return this.variables;
    }

    public int[] getFunctions() {
        return this.functions;
    }

    public int[] getNumbers() {
        return this.numbers;
    }

    public Tuple<Integer, Integer>[] getParenthesis() {
        return this.parenthesis;
    }

    public Tuple<Integer, Integer>[] getPrevTerm() {
        return this.prevTerm;
    }

    public Tuple<Integer, Integer>[] getRedexes() {
        return this.redexes;
    }

    public int getNextRedex() {
        return this.nextRedex;
    }

}
