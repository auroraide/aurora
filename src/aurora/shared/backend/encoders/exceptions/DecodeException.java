package aurora.shared.backend.encoders.exceptions;

/**
 * Something went wrong during decode.
 */
public class DecodeException extends Exception {

    public DecodeException() {}

    public DecodeException(String message) {
        super(message);
    }

}
