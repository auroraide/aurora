package aurora.client.presenter;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.tree.Term;

public class Step {
    private final Term term;
    private final HighlightableLambdaExpression hle;

    Step(Term term, HighlightableLambdaExpression hle) {
        this.term = term;
        this.hle = hle;
    }

    public Term getTerm() {
        return this.term;
    }

    public HighlightableLambdaExpression getHle() {
        return hle;
    }
}
