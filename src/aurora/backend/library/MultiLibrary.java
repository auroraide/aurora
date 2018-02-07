package aurora.backend.library;

import aurora.backend.library.exceptions.LibraryItemNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MultiLibrary implements Library {
    private final List<Library> libraries;
    private final HashMap<String, LibraryItem> libraryItems; // excess

    public MultiLibrary(List<Library> libraries) {
        this.libraries = libraries;
        libraryItems = new HashMap<>();
    }

    public MultiLibrary(Library... libraries) {
        this.libraries = Arrays.asList(libraries);
        libraryItems = new HashMap<>();
    }

    @Override
    public LibraryItem getItem(String name) throws LibraryItemNotFoundException {
        LibraryItem item = libraryItems.get(name);
        if (item != null) {
            return item;
        }

        for (Library library : libraries) {
            if (library.exists(name)) {
                return library.getItem(name);
            }
        }
        throw new LibraryItemNotFoundException();
    }

    @Override
    public void define(LibraryItem item) {
        libraryItems.put(item.getName(), item);
    }

    @Override
    public void define(Library library) {
        libraries.add(library);
    }

    @Override
    public void remove(String name) {
        libraryItems.remove(name);
        libraries.forEach(lib -> lib.remove(name));
    }

    @Override
    public boolean exists(String name) {
        return libraryItems.containsKey(name) || libraries.stream().anyMatch(lib -> lib.exists(name));

    }

    @Override
    public Iterator<LibraryItem> iterator() {
        return new Iterator<LibraryItem>() {
            private Iterator<Library> libraryIterator;
            private Iterator<LibraryItem> currentIterator;

            {
                currentIterator = libraryItems.entrySet().stream().map(Map.Entry::getValue).iterator();
            }

            @Override
            public boolean hasNext() {
                if (currentIterator.hasNext()) {
                    return true;
                }

                if (libraryIterator.hasNext()) {
                    currentIterator = libraryIterator.next().iterator();
                    return hasNext();
                }

                return false;
            }

            @Override
            public LibraryItem next() {
                return null;
            }
        };
    }
}
