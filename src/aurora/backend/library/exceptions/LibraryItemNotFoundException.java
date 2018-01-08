package aurora.backend.library.exceptions;

/**
 * Library item could not be found.
 */
public class LibraryItemNotFoundException extends Exception {

    public LibraryItemNotFoundException() {}

    public LibraryItemNotFoundException(String message) {
        super(message);
    }

}
