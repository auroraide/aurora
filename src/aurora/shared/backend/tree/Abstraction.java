package aurora.shared.backend.tree;

import aurora.shared.backend.visitors.TermVisitor;

/**
 * An Abstraction is a "lambda", a "variable" a "." and a "body" in this order. The body is a Term.
 * The "lambda" and the "." don't have to be saved only the variable and the body.
 */
public class Abstraction extends Term {

    private final Term body;

	private final String name;

	/**
	 * The Abstractions gets a body as a term. It also gets a string as a name for the variable.
     * The name has to consist of lower case letters
	 */
    public Abstraction(Term body, String name) {
        this.body = body;
		this.name = name;
    }


	@Override
	public <T> T accept(TermVisitor<T> visitor) {
        //this.body.accept(visitor);
        return visitor.visit(this);
	}

    /**
     * Standard getter, it returns the body
     * @return The body of the abstraction. It is a Term
     */
	public Term getBody() {
        return this.body;
    }

    /**
     * Standard getter, it returns the name of the variable
     * @return The name of the variable
     */
	public String getName() {
        return this.name;
    }

    /**
     * This method takes the numerical number and returns the number as a lambda expression. Every number can be represented as an abstraction
     * @return the number which got converted into an abstraction
     */
    public Abstraction getAbstraction() {
        return null;
    }

}
