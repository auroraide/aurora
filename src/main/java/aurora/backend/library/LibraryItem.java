package aurora.backend.library;

import aurora.backend.tree.Term;

/**
 * A single item (i.e., lambda term definition) defined in the library.
 */
public class LibraryItem {

    private final String name;

    private final String description;

    private final Term term;

    /**
     * Constructor that initializes a Library item.
     *
     * @param name        The name of the library item.
     * @param description An optional description.
     * @param term        The lambda term that defines this item.
     */
    public LibraryItem(String name, String description, Term term) {
        this.name = name;
        this.description = description;
        this.term = term;
    }

    /**
     * Get the library item name.
     *
     * @return The library item name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the library item description.
     *
     * @return The library item description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get the library item Term.
     *
     * @return The library item Term.
     */
    public Term getTerm() {
        return this.term;
    }

}
