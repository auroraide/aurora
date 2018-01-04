package aurora.shared.backend;

/**
 *
 */
public interface TermVisitor<T> {

    /**
     *
     */
    public T visit(Abstraction abs);

    /**
     *
     */
    public T visit(Application app);

    /**
     *
     */
    public T visit(BoundVariable bvar);

    /**
     *
     */
    public T visit(FreeVariable fvar);

    /**
     *
     */
    public T visit(LibraryTerm libterm);

    /**
     *
     */
    public T visit(ChurchNumber c);


    T visit(Parenthesis p);
}
