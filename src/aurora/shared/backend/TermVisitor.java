package aurora.shared.backend;

import aurora.shared.backend.tree.*;

/**
 *
 */
public interface TermVisitor<T> {

    /**
     *
     * @param abs
     * @return
     */
    public T visit(Abstraction abs);

    /**
     *
     * @param app
     * @return
     */
    public T visit(Application app);

    /**
     *
     * @param bvar
     * @return
     */
    public T visit(BoundVariable bvar);

    /**
     *
     * @param fvar
     * @return
     */
    public T visit(FreeVariable fvar);

    /**
     *
     * @param libterm
     * @return
     */
    public T visit(LibraryTerm libterm);

    /**
     *
     * @param c
     * @return
     */
    public T visit(ChurchNumber c);


    /**
     *
     * @param p
     * @return
     */
    public T visit(Parenthesis p);

}
