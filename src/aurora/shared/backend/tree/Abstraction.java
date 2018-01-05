package aurora.shared.backend.tree;

import aurora.shared.backend.TermVisitor;

/**
 *
 */
public class Abstraction extends Term {

    private final Term body;

	private final String name;

	/**
	 *
	 */
    public Abstraction(Term body, String name) {
        this.body = body;
		this.name = name;
    }

	/**
	 *
	 */
	@Override
	public <T> T accept(TermVisitor<T> visitor) {
        //this.body.accept(visitor);
        return visitor.visit(this);
	}

	/**
	 *
	 */
	public Term getBody() {
        return this.body;
    }

	/**
	 *
	 */
	public String getName() {
        return this.name;
    }

}
