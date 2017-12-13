package aurora.shared.backend;

public class Abstraction extends Term {
	private final BoundVariable boundVariable;
    private final Term term;

    public Abstraction(BoundVariable boundVariable, Term term) {
        this.boundVariable = boundVariable;
        this.term = term;
    }
}
