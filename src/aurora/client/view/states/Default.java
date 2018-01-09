package aurora.client.view.states;

public class Default implements State {
    public State run() { return new Running(); }

    @Override
    public State pause() {
        return null;
    }

    @Override
    public State resume() {
        return null;
    }

    @Override
    public State reset() {
        return null;
    }

    @Override
    public State step() {
        return null;
    }
}
