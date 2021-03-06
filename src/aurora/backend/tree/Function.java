package aurora.backend.tree;

import aurora.backend.TermVisitor;

/**
 * Represents a reference to a library term.
 */
public class Function extends Term {

    public final String name;
    public final Term term;

    /**
     * The constructor of the class gets a String (which starts with a $),
     * which is used as the name of the library term.
     *
     * @param name The name of the library term.
     */
    public Function(String name, Term term) {
        this.name = name;
        this.term = term;
    }

    @Override
    public <T> T accept(TermVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
