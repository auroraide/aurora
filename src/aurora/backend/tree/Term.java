package aurora.backend.tree;

import aurora.backend.TermVisitor;

/**
 * Term is the main class of the package "tree". Every other class in this package extends term.
 * Term accepts a visitor and is the "Element" in the visitor pattern.
 * Every other class that extends Term in this package is the "ConcreteElement" in the visitor pattern.
 */
public abstract class Term {

    /**
     * This method accepts a visitor.
     *
     * @param visitor this is the visitor which is accepted by the term
     * @param <T>     Generic type of the visitor's return value.
     * @return Whatever the visitor wishes to return.
     */
    public abstract <T> T accept(TermVisitor<T> visitor);

}
