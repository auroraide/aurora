package aurora.backend.encoders;

import aurora.backend.encoders.exceptions.DecodeException;
import aurora.backend.library.Library;
import aurora.backend.encoders.Session;

/**
 * Encode and decode facilities to save and restore sessions (i.e., raw lambda code along with Library entries).
 */
public abstract class SessionEncoder {

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
     * @param library  The Library object to be encoded.
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

