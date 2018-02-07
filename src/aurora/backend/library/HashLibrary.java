package aurora.backend.library;

import aurora.backend.library.exceptions.LibraryItemNotFoundException;
import aurora.backend.tree.Term;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Collection of lambda term definitions.
 */
public class HashLibrary implements Library {

    private Map<String, LibraryItem> map;

    /**
     * Standard constructor, that initializes an empty hash map.
     */
    public HashLibrary() {
        this.map = new HashMap<>();
    }

    @Override
    public Iterator<LibraryItem> iterator() {
        return null;
    }

    /**
     * Get the LibraryItem by name.
     *
     * @param name The name of the library item.
     * @return The LibraryItem associated with the given name.
     * @throws LibraryItemNotFoundException If there is no such entry in the library.
     */
    @Override
    public LibraryItem getItem(String name) throws LibraryItemNotFoundException {
        return this.map.get(name);
    }

    /**
     * Create a new LibraryItem and add it to the Library.
     *
     * @param name        The name of the Library item to be added.
     * @param description The optional description of the Library item to be added.
     * @param term        The term of the Library item to be added.
     */
    @Override
    public void define(String name, String description, Term term) {
        this.define(new LibraryItem(name, description, term));
    }

    /**
     * Add a {@link LibraryItem} to the Library.
     *
     * @param item The {@link LibraryItem} instance to be added.
     */
    @Override
    public void define(LibraryItem item) {
        this.map.put(item.getName(), item);
    }

    /**
     * Add the content of whole other Library to this instance.
     *
     * @param library The {@link HashLibrary} instance to be added.
     */
    @Override
    public void define(Library library) {
        for (LibraryItem i : library) {
            this.define(i);
        }
    }

    /**
     * Remove a LibraryItem from the Library by name.
     *
     * @param name Name of the LibraryItem that should be removed.
     */
    @Override
    public void remove(String name) {

    }

    /**
     * Check if a given LibraryItem exists in the Library.
     *
     * @param name The name of the LibraryItem.
     * @return Whether the LibraryItem exists in the Library.
     */
    @Override
    public boolean exists(String name) {
        return false;
    }

}
