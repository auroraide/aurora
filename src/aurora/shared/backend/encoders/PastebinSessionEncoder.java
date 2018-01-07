package aurora.shared.backend.encoders;

import aurora.shared.backend.encoders.exceptions.DecodeException;
import aurora.shared.backend.library.Library;

/**
 * Save/restore sessions on <a href="https://pastebin.com">pastebin.com</a>.
 */
public class PastebinSessionEncoder extends SessionEncoder {

    @Override
    public String encode(Session session) {
        return null;
    }

    @Override
    public Session decode(String encodedInput) throws DecodeException {
        return null;
    }
    
}
