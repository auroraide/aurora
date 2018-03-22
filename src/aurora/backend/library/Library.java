package aurora.backend.library;

import aurora.backend.library.exceptions.LibraryItemNotFoundException;
import aurora.backend.tree.Term;
import java.util.Iterator;

public interface Library extends Iterable<LibraryItem> {
    LibraryItem getItem(String name) throws LibraryItemNotFoundException;

    default void define(String name, String description, Term term) {
        define(new LibraryItem(name, description, term));
    }

    void define(LibraryItem item);

    void define(Library library);

    void remove(String name);

    boolean exists(String name);

    void clear();
}
