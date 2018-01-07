package aurora.shared.backend;

import aurora.shared.backend.tree.Term;

import java.util.HashMap;
import java.util.Map;

/**
 * Collection of lambda term definitions.
 */
public class Library {

    private Map<String, LibraryItem> map;

    /**
     * Standard constructor.
     */
    public Library() {
        this.map = new HashMap<>();
    }

    /**
     * A single item (i.e., lambda term definition) of the library.
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
         * Get the Term.
         *
         * @param name the name of the library item.
         * @return the term that has this name.
         */
        public String getName() {
            return this.name;
        }

        public String getDescription() {
            return this.description;
        }

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
        return this.map.get(name).term;
    }

    /**
     * This creates a new library item and adds it to the map.
     *
     * @param name the name of the library item.
     * @param description the description of the library item.
     * @param term the term of the library item.
     */
    public void define(String name, String description, Term term) {}

}
