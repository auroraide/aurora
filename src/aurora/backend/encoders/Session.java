package aurora.backend.encoders;

import aurora.backend.library.Library;

/**
 * A Session contains the input as well as the library.
 */
public class Session {

    public final String rawInput;
    public final Library library;

    /**
     * Construct a Session from raw input and a Library instance.
     *  
     * @param rawInput The raw input string.
     * @param library The library instance.
     */
    public Session(String rawInput, Library library) {
        this.rawInput = rawInput;
        this.library = library;
    }

}

