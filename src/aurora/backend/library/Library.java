package aurora.backend.library;

import aurora.backend.library.exceptions.LibraryItemNotFoundException;
import aurora.backend.tree.Term;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Collection of lambda term definitions.
 */
public class Library {

    private Map<String, LibraryItem> map;

    /**
     * Standard constructor, that initializes an empty hash map.
     */
    public Library() {
        this.map = new HashMap<>();
    }

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

    /**
     * Get the LibraryItem by name.
     *
     * @param name The name of the library item.
     * @return The LibraryItem associated with the given name.
     * @throws LibraryItemNotFoundException If there is no such entry in the library.
     */
    public LibraryItem getItem(String name) {
        return this.map.get(name);
    }

    /**
     * Create a new LibraryItem and add it to the Library.
     *
     * @param name The name of the Library item to be added.
     * @param description The optional description of the Library item to be added.
     * @param term The term of the Library item to be added.
     */
    public void define(String name, String description, Term term) {
        this.define(new LibraryItem(name, description, term));
    }

    /**
     * Add a LibraryItem to the Library.
     *
     * @param item The LibraryItem instance to be added.
     */
    public void define(LibraryItem item) {
        this.map.put(item.name, item);
    }

    /**
     * Add the content of whole other Library to this instance.
     *
     * @param library
     */
    public void define(Library library) {
        for (Map.Entry<String, LibraryItem> entry : library.map.entrySet()) {
            this.define(entry.getValue());
        }
    }

    /**
     * Remove a LibraryItem by name.
     *
     * @param name Name of the LibraryItem that should be removed.
     */
    public void remove(String name) {

    }

    /**
     * Checks if the function is already in the Library.
     *
     * @param name The name of the function.
     *
     * @return The boolean if the name is already in the Library.
     */
    public boolean hasTerm(String name) {
        return false;
    }
}
