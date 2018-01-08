package aurora.backend;

import aurora.backend.tree.*;

/**
 * Term tree Visitor with generic return type.
 *
 * @param <T> Return type of the visit methods.
 */
public interface TermVisitor<T> {
    /**
     * Called with Abstraction.
     *
     * @param abs The caller.
     * @return Result or null.
     */
    T visit(Abstraction abs);

    /**
     * Called with Application.
     *
     * @param app The caller.
     * @return Result or null.
     */
    T visit(Application app);

    /**
     * Called by BoundVariables.
     *
     * @param bvar The caller.
     * @return Result or null.
     */
    T visit(BoundVariable bvar);

    /**
     * Called with FreeVariable.
     *
     * @param fvar The caller.
     * @return Result or null.
     */
    T visit(FreeVariable fvar);

    /**
     * Called with LibraryTerm.
     *
     * @param libterm The caller.
     * @return Result or null.
     */
    T visit(LibraryTerm libterm);

    /**
     * Called with ChurchNumber.
     *
     * @param c The caller.
     * @return Result or null.
     */
    T visit(ChurchNumber c);

    /**
     * Called with Parenthesis.
     *
     * @param p The caller.
     * @return Result or null.
     */
    T visit(Parenthesis p);

}
