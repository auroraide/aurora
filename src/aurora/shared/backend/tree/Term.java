package aurora.shared.backend.tree;

import aurora.shared.backend.visitors.TermVisitor;

/**
 *
 */
public abstract class Term {

	/**
	 *
	 */
	public abstract <T> T accept(TermVisitor<T> visitor);

}
