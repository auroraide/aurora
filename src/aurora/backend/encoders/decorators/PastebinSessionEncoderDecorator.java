package aurora.backend.encoders.decorators;

import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

import aurora.backend.encoders.SessionEncoder;
import aurora.backend.encoders.exceptions.DecodeException;

/**
 * Decorate {@link SessionEncoder}s with the ability to save/restore
 * sessions on <a href="https://pastebin.com">pastebin.com</a> in order to make the resulting string (way) shorter.
 */
public class PastebinSessionEncoderDecorator extends SessionEncoderDecorator {

    public PastebinSessionEncoderDecorator() {
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


    public String testPaste(String code) {
        return new Paste(code).upload();
    }



    private class Paste {
        private static final String POST_URL = "https://api.github.com/gists";
        private static final String API_KEY = "e09a05a00409086d72e3dfae85d341c8";
        
        private String code;
        public String url = null;

        public Paste(String code) {
            this.code = code;
        }
        
        private native void console(String message) /*-{
            console.log(message);
        }-*/;
    
        private String upload() {
            RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, POST_URL);
            try {
                Request response = builder.sendRequest(
                        "{ \"files\" :{"
                            + "\"file1.txt\": {"
                                + "\"content\": \"File content\""
                            + "}"
                        + "}}",
                        new RequestCallback() {
                    public void onError(Request request, Throwable exception) {
                        throw new RuntimeException("Error reveivung from Pastebin...");
                    }
                    public void onResponseReceived(Request request, Response response) {
                        console(response.getText().toString());
                    }
                });
            } catch (RequestException e) {
                throw new RuntimeException("Error Pastebin");
            }
            return "Banana";

        }

    }

}
