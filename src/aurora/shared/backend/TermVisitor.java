package aurora.shared.backend;

/**
 *
 */
public interface TermVisitor {

    /**
     *
     */
    public void visit(Abstraction abs);

    /**
     *
     */
    public void visit(Application app);

    /**
     *
     */
    public void visit(BoundVariable bvar);

    /**
     *
     */
    public void visit(FreeVariable fvar);

    /**
     *
     */
    public void visit(LibraryTerm libterm);

    /**
     *
     */
    public void visit(ChurchNumber c);

}
