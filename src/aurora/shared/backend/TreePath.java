package aurora.shared.backend;

import java.util.Iterator;
import java.util.LinkedList;

public class TreePath implements Iterable<TreePath.Direction> {

    private final LinkedList<Direction> path;

    public TreePath() {
        this.path = new LinkedList<Direction>();
    }

    public void append(Direction d) {
        this.path.add(d);
    }
    public void remove() {
        this.path.pop();
    }
    @Override
    public Iterator<Direction> iterator() {
        return this.path.iterator();
    }

    public enum Direction { LEFT, RIGHT }


}
