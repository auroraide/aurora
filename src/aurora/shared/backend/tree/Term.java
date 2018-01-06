package aurora.shared.backend.tree;

import aurora.shared.backend.visitors.TermVisitor;

/**
 * The term class is the main class of the package "tree". Every other class in the Package extends Term
 * The Term class accepts a visitor and is the "Element" in the visitor pattern.
 * Every other class in this package is the "ConcreteElement"
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
