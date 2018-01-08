package aurora.backend.encoders;

import aurora.backend.library.Library;
import aurora.backend.encoders.exceptions.*;

/**
 * Encode and decode facilities to save and restore sessions (i.e., raw lambda code along with Library entries).
 */
public abstract class SessionEncoder {

    /**
     * A Session is lambda code (e.g., from user input) along with some Library context.
     */
    public class Session {

        public final String rawInput;

        public final Library library;

        /**
         * Construct a Session from raw input and Library instance.
         * @param rawInput The raw input string.
         * @param library The Library instance.
         */
        public Session(String rawInput, Library library) {
            this.rawInput = rawInput;
            this.library = library;
        }

    }

    /**
     * Encode a Session to a string.
     *
     * @param session Session to be encoded.
     * @return Encoded string.
     */
    public abstract String encode(Session session);

    /**
     * Encode raw input String along with a Library to a string.
     * This is just a helper that creates the Session object for you.
     *
     * @param rawInput The raw input to be encoded.
     * @param library The Library object to be encoded.
     * @return Encoded string.
     */
    public String encode(String rawInput, Library library) {
        return this.encode(new Session(rawInput, library));
    }

    /**
     * Decode some previously encoded string.
     *
     * @param encodedInput The encoded string.
     * @return The decoded Session.
     * @throws DecodeException If the encoded input string could not be decoded.
     */
    public abstract Session decode(String encodedInput) throws DecodeException;

}
