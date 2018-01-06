package aurora.shared.backend.visitors;

import aurora.shared.backend.tree.*;

/**
 * In the Visitor pattern, this class is the "visitor interface".
 * @param <T> Return type.
 */
public interface TermVisitor<T> {
    /**
     * Called by Abstractions.
     * @param abs The caller.
     * @return Result or null.
     */
    T visit(Abstraction abs);

    /**
     * Called by Applications.
     * @param app The caller.
     * @return Result or null.
     */
    T visit(Application app);

    /**
     * Called by BoundVariables.
     * @param bvar The caller.
     * @return Result or null.
     */
    T visit(BoundVariable bvar);

    /**
     * Called by FreeVariables.
     * @param fvar The caller.
     * @return Result or null.
     */
    T visit(FreeVariable fvar);

    /**
     * Called by LibraryTerms.
     * @param libterm The caller.
     * @return Result or null.
     */
    T visit(LibraryTerm libterm);

    /**
     * Called by ChurchNumbers.
     * @param c The caller.
     * @return Result or null.
     */
    T visit(ChurchNumber c);

    /**
     * Called by Parenthesiseses.
     * @param p The caller.
     * @return Result or null.
     */
    T visit(Parenthesis p);

}
