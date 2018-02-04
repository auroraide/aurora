package aurora.backend;

import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;

/**
 * Term tree Visitor with generic return type.
 *
 * @param <T> Return type of the visit methods.
 */
public abstract class TermVisitor<T> {
    /**
     * Called with {@link Abstraction}.
     *
     * @param abs The caller.
     * @return Result or null.
     */
    public abstract T visit(Abstraction abs);

    /**
     * Called with {@link Application}.
     *
     * @param app The caller.
     * @return Result or null.
     */
    public abstract T visit(Application app);

    /**
     * Called by {@link BoundVariable}.
     *
     * @param bvar The caller.
     * @return Result or null.
     */
    public abstract T visit(BoundVariable bvar);

    /**
     * Called with {@link FreeVariable}.
     *
     * @param fvar The caller.
     * @return Result or null.
     */
    public abstract T visit(FreeVariable fvar);

    /**
     * Called with {@link Function}.
     *
     * @param libterm The caller.
     * @return Result or null.
     */
    public abstract T visit(Function libterm);

    /**
     * Called with {@link ChurchNumber}.
     *
     * @param c The caller.
     * @return Result or null.
     */
    public abstract T visit(ChurchNumber c);

    /**
     * Called with {@link MetaTerm}.
     * The default implementation is to just skip any {@link MetaTerm}.
     *
     * @param mt {@link MetaTerm} that shall be visited.
     * @return Result or null.
     */
    public T visit(MetaTerm mt) {
        return mt.term.accept(this);
    }

}
