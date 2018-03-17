package aurora.client.presenter;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.tree.Term;

public class Step implements Step {
    private final Term term;
    private final HighlightableLambdaExpression hle;

    Step(Term term, HighlightableLambdaExpression hle) {
        this.term = term;
        this.hle = hle;
    }

    @Override
    public Term getTerm() {
        return null;
    }

    public HighlightableLambdaExpression getHle() {
        return hle;
    }
}
