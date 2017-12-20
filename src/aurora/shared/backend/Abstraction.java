package aurora.shared.backend;

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
	public void accept(TermVisitor visitor) {

	}

	/**
	 *
	 */
	public Term getBody() {}

	/**
	 *
	 */
	public String getName() {}

}
