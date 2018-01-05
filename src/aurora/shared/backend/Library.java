package aurora.shared.backend;

import aurora.shared.backend.tree.Term;

import java.util.Map;

/**
 * Usually there should be two instances of this class: Standard library, and then the user library.
 */
public class Library {
    public class LibraryItem {
        private String name;
        private String description;
        private Term term;
    }

    private Map<String, LibraryItem> map;

    public LibraryItem getTerm(String name) {
        return null;
    }

    public void defTerm(LibraryItem entry) { }
}
