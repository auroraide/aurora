package aurora.shared.backend;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreePath implements Iterable<TreePath.Direction> {
    public TreePath() {
        path = new LinkedList<>();
    }

    public void append(Direction d) { path.add(d); }

    @Override
    public Iterator<Direction> iterator() {
        return path.iterator();
    }

    public enum Direction { LEFT, RIGHT }

    private final List<Direction> path;
}
