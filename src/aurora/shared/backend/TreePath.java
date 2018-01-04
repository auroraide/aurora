package aurora.shared.backend;

public class TreePath {
    public TreePath(Direction[] path) {
        this.path = path;
    }

    public Direction getDirection(int index) {
        return path[index];
    }

    public int getLength() {
        return path.length;
    }

    public enum Direction { LEFT, RIGHT }

    private final Direction[] path;
}
