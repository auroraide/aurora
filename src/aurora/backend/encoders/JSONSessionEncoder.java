package aurora.backend.encoders;

import aurora.backend.encoders.exceptions.DecodeException;

/**
 * Serialize/deserialize a {@link Session} into a JSON string.
 */
public class JSONSessionEncoder extends SessionEncoder {

    @Override
    public String encode(Session session) {
        // TODO actually serialize session to json
        return null;
    }

    @Override
    public Session decode(String encodedInput) throws DecodeException {
        // TODO actually deserialize json to session
        return null;
    }

}
