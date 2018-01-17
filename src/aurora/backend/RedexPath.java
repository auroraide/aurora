package aurora.backend;

import aurora.backend.tree.Application;
import aurora.backend.tree.Term;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A {@link RedexPath} is a series of left and right instructions that point
 * to an {@link Application} within a tree of {@link Term}s.
 */
public class RedexPath implements Iterable<RedexPath.Direction> {

    private final LinkedList<Direction> path;

    /**
     * This constructor initializes an empty {@link RedexPath}.
     */
    public RedexPath() {
        this.path = new LinkedList<>();
    }

    /**
     * Add a new enum to the List.
     *
     * @param d The enum left or right.
     */
    public void push(Direction d) {
        this.path.add(d);
    }

    /**
     * Deletes the last element of the list.
     */
    public void pop() {
        this.path.pop();
    }

    @Override
    public Iterator<Direction> iterator() {
        return this.path.iterator();
    }

    /**
     * A {@link Term} gets traversed like the {@link RedexPath} describes and returns
     * the {@link Application} that it points to.
     *
     * @param term The term that will get traversed.
     * @return The {@link Application} that we're pointing to.
     */
    public Application get(Term term) {
        return null;
    }

    /**
     * Indicate the direction during tree traversal.
     */
    public enum Direction {
        LEFT, RIGHT
    }

}
