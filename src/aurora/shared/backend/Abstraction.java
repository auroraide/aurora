package aurora.shared.backend;

public class Abstraction extends Term {

	private final BoundVariable variable;

    private final Term body;

	private final String name;

    public Abstraction(BoundVariable variable, Term body, String name) {
        this.variable = variable;
        this.body = body;
		this.name = name;
    }

}
