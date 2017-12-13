package aurora.shared.backend;

public class BoundVariable extends Variable {
    private final Abstraction abstraction;

    public BoundVariable(Abstraction abstraction) {
        this.abstraction = abstraction;
    }
}
