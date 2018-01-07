package aurora.shared.backend.encoders;

import aurora.shared.backend.library.Library;
import aurora.shared.backend.encoders.exceptions.*;

/**
 * Encode and decode facilities to save and restore sessions (i.e., raw lambda code along with Library entries).
 */
public abstract class SessionEncoder {

    public class Session {

        public final String rawInput;

        public final Library library;

        public Session(String rawInput, Library library) {
            this.rawInput = rawInput;
            this.library = library;
        }

    }

    /**
     * Encode raw input and Library entries.
     *
     * @param rawInput Some raw user input.
     * @param library Library object.
     * @return Encoded String containing both input and Library.
     */
    public String encode(String rawInput, Library library);

    /**
     * Decode some previously encoded String.
     * The user input and Library entries have to be fetched using getLibrary().
     *
     * @param encodedInput The encoded String.
     * @return The decoded input.
     */
    public void decode(String encodedInput) throws DecodeException;

    /**
     * Get decoded Library object.
     *
     * @return The decoded Library if possible.
     */
    public Library getLibrary();

    /**
     * Get decoded input.
     *
     * @return The decoded input.
     */
    public String getInput();

}
