package aurora.shared.backend.visitors;

import aurora.shared.backend.HighlightedLambdaExpression;
import aurora.shared.backend.tree.*;

/**
 * This class computes the HighligtedLambdaTerm representation of the Term it is applied on.
 */
class TermToHighlightedLambdaExpressionVisitor implements TermVisitor<HighlightedLambdaExpression> {
    @Override
    public HighlightedLambdaExpression visit(Abstraction abs) {
        return null;
    }

    @Override
    public HighlightedLambdaExpression visit(Application app) {
        return null;
    }

    @Override
    public HighlightedLambdaExpression visit(BoundVariable bvar) {
        return null;
    }

    @Override
    public HighlightedLambdaExpression visit(FreeVariable fvar) {
        return null;
    }

    @Override
    public HighlightedLambdaExpression visit(LibraryTerm libterm) {
        return null;
    }

    @Override
    public HighlightedLambdaExpression visit(ChurchNumber c) {
        return null;
    }

    @Override
    public HighlightedLambdaExpression visit(Parenthesis p) {
        return null;
    }
}
