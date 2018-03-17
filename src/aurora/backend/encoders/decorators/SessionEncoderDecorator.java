package aurora.backend.encoders.decorators;

import aurora.backend.encoders.SessionEncoder;
import aurora.backend.encoders.exceptions.DecodeException;
import aurora.backend.encoders.Session;

/**
 * Decorator for {@link SessionEncoder} objects.
 */
public abstract class SessionEncoderDecorator extends SessionEncoder {

    protected SessionEncoder sessionEncoder;

    protected SessionEncoderDecorator() {
        // This page is intentionally left blank.
    }

    /**
     * Construct a {@link SessionEncoderDecorator} wrapped around the concrete {@link SessionEncoder}.
     *
     * @param se The concrete {@link SessionEncoder} that shall be decorated.
     */
    public SessionEncoderDecorator(SessionEncoder se) {
        this.sessionEncoder = se;
    }

    @Override
    public String encode(Session session) {
        return this.sessionEncoder.encode(session);
    }

    @Override
    public Session decode(String encodedInput) throws DecodeException {
        return this.sessionEncoder.decode(encodedInput);
    }
}
