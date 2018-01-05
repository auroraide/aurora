package aurora.shared.backend;

import aurora.shared.backend.tree.Application;
import aurora.shared.backend.tree.Term;

import java.util.Iterator;
import java.util.LinkedList;

public class TreePath implements Iterable<TreePath.Direction> {
    private final LinkedList<Direction> path;
    private final Term term;

    public TreePath(Term term) {
        this.term = term;
        this.path = new LinkedList<>();
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

    public Application get() {
        return null;
    }

    public enum Direction { LEFT, RIGHT }


}
