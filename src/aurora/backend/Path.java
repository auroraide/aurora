package aurora.backend;

import aurora.backend.tree.*;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A {@link Path} is a series of left and right instructions that point.
 * to an {@link Application} within a tree of {@link Term}s.
 */
public class Path implements Iterable<Path.Direction> {

    private final LinkedList<Direction> path;

    /**
     * This constructor initializes an empty {@link Path}.
     */
    public Path() {
        this.path = new LinkedList<>();
    }

    public Path(LinkedList<Direction> path) {
        this.path = path;
    }

    @Override
    public Path clone() {
        return new Path((LinkedList<Direction>) path.clone());
    }

    /**
     * Checks if both point to the same redex. comapres only path.
     * @param other other
     * @return whether same.
     */
    public boolean isSame(Path other) {
        if (other.path.size() != path.size()) {
            return false;
        }

        for (int i = 0; i < path.size(); i++) {
            if (path.get(i) != other.path.get(i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Add a new enum to the List.
     *
     * @param d The enum left or right.
     */
    public void push(Direction d) {
        this.path.addLast(d);
    }

    /**
     * Deletes the last added element of the list.
     */
    public void pop() {
        this.path.removeLast();
    }

    @Override
    public Iterator<Direction> iterator() {
        return this.path.iterator();
    }

//    /**
//     * A {@link Term} gets traversed like the {@link Path} describes and returns
//     * the {@link Term} that it points to.
//     *
//     * @param term The term that will get traversed.
//     * @return The {@link Term} that we're pointing to.
//     */
//    public Term get(Term term) {
//        return term.accept(new Walker());
//    }

    /**
     * standard getter for the direction.
     * @return the direction to a redex.
     */
    public LinkedList<Direction> getPath() {
        return path;
    }

    /**
     * Indicate the direction during tree traversal.
     */
    public enum Direction {
        LEFT, RIGHT, STRAIGHT
    }

//    /**
//     * this visitor finds the application to which the path is pointing.
//     */
//    private static class Walker extends TermVisitor<Term> {
//        private Path path;
//
//        public Walker(Path path) {
//            this.path = path;
//        }
//
//        @Override
//        public Term visit(Abstraction abs) {
//            path.pop();
//            abs.accept(this);
//        }
//
//        @Override
//        public Term visit(Application app) {
//            return null;
//        }
//
//        @Override
//        public Term visit(BoundVariable bvar) {
//            return null;
//        }
//
//        @Override
//        public Term visit(FreeVariable fvar) {
//            return null;
//        }
//
//        @Override
//        public Term visit(Function libterm) {
//            return null;
//        }
//
//        @Override
//        public Term visit(ChurchNumber c) {
//            return null;
//        }
////
////        private int counter = 0;
////        @Override
////        public Void visit(Abstraction abs) {
////            return abs.body.accept(this);
////
////        }
////
////        @Override
////        public Void visit(Application app) {
////            if (counter == path.size()) {
////                foundapp = true;
////                finalapp = app;
////
////            } else {
////                if (path.get(counter) == Direction.LEFT) {
////                    counter++;
////                    return app.left.accept(this);
////                }
////                if (path.get(counter) == Direction.RIGHT) {
////                    counter++;
////                    return app.right.accept(this);
////                }
////
////
////
////            }
////            return null;
////        }
////
////        @Override
////        public Void visit(BoundVariable bvar) {
////           assert false : "Tree is finished but no Application. This should never happen";
////            return null;
////        }
////
////        @Override
////        public Void visit(FreeVariable fvar) {
////            assert false : "Tree is finished but no Application. This should never happen";
////            return null;
////        }
////
////        @Override
////        public Void visit(Function libterm) {
////            return libterm.term.accept(this);
////        }
////
////        @Override
////        public Void visit(ChurchNumber c) {
////            assert false : "Searching for a Redex below Churchnumber, this can't happen";
////            return null;
////        }
//    }

}

