package aurora.shared.backend;

import aurora.shared.backend.tree.Term;

import java.util.Map;

/**
 * Usually there should be two instances of this class: Standard library, and then the user library. The Library contains LibraryItems in  a map.
 * The map has a name as key and library items as values.
 */
public class Library {

    /**
     * This is a nested class in library.
     * A LibraryItem has a name as a string, a description as a string and a term as a term.
     * The name of the LibraryItem starts with a $.
     * The description isn't needed but can be added to a name.
     * One name has one term.
     */
    private class LibraryItem {
        private String name;
        private String description;
        private Term term;


    }


    private Map<String, LibraryItem> map;

    /**
     * This is a getter which returns the library item , that has the wanted name.
     * @param name the name of the library item.
     * @return the term that has this name.
     */
    public Term getTerm(String name) {
        return this.map.get(name).term;
    }

    /**
     * This creates a new library item and adds it to the map.
     * @param name the name of the library item.
     * @param description the description of the library item.
     * @param term the term of the library item.
     */
    public void defTerm(String name, String description, Term term) { }
}
