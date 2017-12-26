package aurora.shared.backend;

/**
 *
 */
public abstract class Term {

	/**
	 *
	 */
	public abstract <T> T accept(TermVisitor<T> visitor);

}
