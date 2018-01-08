package aurora.shared.backend;

import aurora.shared.backend.tree.Application;
import aurora.shared.backend.tree.Term;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class is a path that traverses a term and calculates an application.
 * The path is a list.
 */
public class TreePath implements Iterable<TreePath.Direction> {
    private final LinkedList<Direction> path;

    /**
     * This constructor initializes an empty TreePath.
     */
    public TreePath() {
        this.path = new LinkedList<>();
    }

    /**
     * Add a new enum to the List.
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
     * A term gets traversed like the tree path says and returns the found application.
     * @param term The term that will get traversed.
     * @return The application the TreePath shows.
     */
    public Application get(Term term) {
        return null;
    }

    /**
     * This enum is used to show the direction of the traversal of an application.
     */
    public enum Direction { LEFT, RIGHT }


}
