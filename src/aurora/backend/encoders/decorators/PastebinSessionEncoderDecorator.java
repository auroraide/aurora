package aurora.backend.encoders.decorators;

import aurora.backend.encoders.SessionEncoder;
import aurora.backend.encoders.exceptions.DecodeException;

/**
 * Decorate {@link SessionEncoder}s with the ability to save/restore
 * sessions on <a href="https://pastebin.com">pastebin.com</a> in order to make the resulting string (way) shorter.
 */
public class PastebinSessionEncoderDecorator extends SessionEncoderDecorator {

    protected PastebinSessionEncoderDecorator() {
        // This page is intentionally left blank.
    }

    /**
     * Construct a {@link PastebinSessionEncoderDecorator} wrapped around the concrete {@link SessionEncoder}.
     *
     * @param se The concrete {@link SessionEncoder} that shall be decorated.
     */
    public PastebinSessionEncoderDecorator(SessionEncoder se) {
        super(se);
    }

    @Override
    public String encode(Session session) {
        String encoded = this.sessionEncoder.encode(session);

        // TODO pastebin upload magic goes here...
        return null;
    }

    @Override
    public Session decode(String encodedInput) throws DecodeException {
        // TODO actually get back original string from pastebin...
        String toBeDecoded = "original string from pastebin";

        // ...then decode it like normal people
        return this.sessionEncoder.decode(toBeDecoded);
    }

}
