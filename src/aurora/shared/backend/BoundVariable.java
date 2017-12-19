package aurora.shared.backend;

public class BoundVariable extends Variable {
    private final Abstraction abstraction;

    public BoundVariable(Abstraction abstraction) {
        this.abstraction = abstraction;
    }

    // in BoundVariable
    public Term substitute(int index, Term with) {
        if (this.index == index) return with;
        return this;
    }
}
