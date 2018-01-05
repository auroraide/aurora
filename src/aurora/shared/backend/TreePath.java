package aurora.shared.backend;

import aurora.shared.backend.tree.Application;
import aurora.shared.backend.tree.Term;

import java.util.Iterator;
import java.util.LinkedList;

public class TreePath implements Iterable<TreePath.Direction> {
    private final LinkedList<Direction> path;

    public TreePath() {
        this.path = new LinkedList<>();
    }

    public void push(Direction d) {
        this.path.add(d);
    }
    public void pop() {
        this.path.pop();
    }
    @Override
    public Iterator<Direction> iterator() {
        return this.path.iterator();
    }

    public Application get(Term term) {
        return null;
    }

    public enum Direction { LEFT, RIGHT }


}
