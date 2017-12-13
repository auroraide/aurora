package aurora;

public class Abstraction extends Term {
    private String name;
	private FreeVariable bound_var;
    private final Term term;

    public Abstraction(FreeVariable bound_var, Term term) {
        this.bound_var = bound_var;
        this.term = term;
    }

    public Term getTerm() {
        return term;
    }
}
