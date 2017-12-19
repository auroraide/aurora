package aurora.shared.backend;

public abstract class Term {

	public abstract void accept(TermVisitor visitor);

	/**
	 * 
	 */
	public abstract Term substitute(int index, Term with);

}
