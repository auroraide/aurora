package aurora.shared.backend;

/**
 *
 */
public class Tuple<T, V> {

    private T first;

    private V second;

    /**
     *
     */
    public T getFirst() {
        return this.first;
    }

    /**
     *
     */
    public V getSecond() {
        return this.second;
    }

    /**
     *
     */
    public void setFirst(T first) {
        this.first = first;
    }

    /**
     *
     */
    public void setSecond(V second) {
        this.second = second;
    }

}
