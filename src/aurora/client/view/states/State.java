package aurora.client.view.states;


public interface State {
    State run();
    State pause();
    State resume();
    State reset();
    State step();

//        default State run() { throw new IllegalStateException(); }
//        default State pause() { throw new IllegalStateException(); }
//        default State resume() { throw new IllegalStateException(); }
//        default State reset() { throw new IllegalStateException(); }
//        default State step() { throw new IllegalStateException(); }
}
