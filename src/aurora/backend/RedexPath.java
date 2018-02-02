package aurora.backend;

import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.LibraryTerm;
import aurora.backend.tree.Term;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A {@link RedexPath} is a series of left and right instructions that point.
 * to an {@link Application} within a tree of {@link Term}s.
 */
public class RedexPath implements Iterable<RedexPath.Direction> {

    private final LinkedList<Direction> path;
    private Application finalapp;
    private boolean foundapp = false;
    private Term parent;
    private int counter;
    private String parenttype;

    /**
     * This constructor initializes an empty {@link RedexPath}.
     */
    public RedexPath() {
        this.path = new LinkedList<>();
        this.counter = 0;
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
     * Deletes the last element of the list.
     */
    public void pop() {
        this.path.removeLast();
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
        if (path == null) {
            System.out.println("There is no Redex here this should never happen");
            return null;
        }
        Walker walker = new Walker();
        term.accept(walker);
        if (foundapp == false) {
            System.out.println("THIS SHOULD NEVER HAPPEN");
        }
        return finalapp;
    }


    /**
     * standard getter for the direction.
     * @return the direction to a redex.
     */
    public LinkedList<Direction> getPath() {
        return path;
    }

    public Term getParent() {
        return parent;
    }

    public String getParenttype() {
        return parenttype;
    }

    /**
     * Indicate the direction during tree traversal.
     */
    public enum Direction {
        LEFT, RIGHT
    }


    /**
     * this visitor finds the application to which the path is pointing.
     */
    private class Walker extends TermVisitor<Void> {


        @Override
        public Void visit(Abstraction abs) {
            parent = abs;
            parenttype = "Abstraction";
            return abs.body.accept(this);

        }

        @Override
        public Void visit(Application app) {
            if (counter == path.size()) {
                foundapp = true;
                finalapp = app;

            } else {
                parent = app;
                parenttype = "Application";
                if (path.get(counter) == Direction.LEFT) {
                    counter++;
                    return app.left.accept(this);
                }
                if (path.get(counter) == Direction.RIGHT) {
                    counter++;
                    return app.right.accept(this);
                }



            }
            return null;
        }

        @Override
        public Void visit(BoundVariable bvar) {
            System.out.println("THIS SHOULD NEVER HAPPEN");
            return null;
        }

        @Override
        public Void visit(FreeVariable fvar) {
            System.out.println("THIS SHOULD NEVER HAPPEN");
            return null;
        }

        @Override
        public Void visit(LibraryTerm libterm) {
            return null;
        }

        @Override
        public Void visit(ChurchNumber c) {
            Abstraction abs = c.getAbstraction();
            abs.accept(this);
            return null;
        }
    }
}

