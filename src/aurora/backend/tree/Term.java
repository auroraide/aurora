package aurora.backend.tree;

import aurora.backend.visitors.TermVisitor;

/**
 * The term class is the main class of the package "tree". Every other class in the package extends term.
 * The Term class accepts a visitor and is the "Element" in the visitor pattern.
 * Every other class that extends Term in this package is the "ConcreteElement" in the visitor pattern.
 */
public abstract class Term {

    /**
     * This method accepts a visitor
     * @param visitor this is the visitor which is accepted by the term
     * @param <T> todo
     * @return todo
     */
	public abstract <T> T accept(TermVisitor<T> visitor);

}
