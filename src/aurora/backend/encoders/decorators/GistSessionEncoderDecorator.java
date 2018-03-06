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

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;

import aurora.backend.encoders.SessionEncoder;
import aurora.backend.encoders.exceptions.DecodeException;

/**
 * Decorate {@link SessionEncoder}s with the ability to save/restore
 * sessions on <a href="https://gist.github.com">gist.github.com</a> in order to make the resulting string (way) shorter.
 */
public class GistSessionEncoderDecorator extends SessionEncoderDecorator {

    public GistSessionEncoderDecorator() {
        // This page is intentionally left blank.
    }

    /**
     * Construct a {@link GistSessionEncoderDecorator} wrapped around the concrete {@link SessionEncoder}.
     *
     * @param se The concrete {@link SessionEncoder} that shall be decorated.
     */
    public GistSessionEncoderDecorator(SessionEncoder se) {
        super(se);
    }

    @Override
    public String encode(Session session) {
        String encoded = this.sessionEncoder.encode(session);

        // TODO gist upload magic goes here...
        return null;
    }

    @Override
    public Session decode(String encodedInput) throws DecodeException {
        // TODO actually get back original string from gist...
        String toBeDecoded = "original string from gist";

        // ...then decode it like normal people
        return this.sessionEncoder.decode(toBeDecoded);
    }


    public String testPaste(String code) {
        return new Paste(code).upload();
    }



    private class Paste {
        private static final String POST_URL = "https://api.github.com/gists";
        
        private String code;
        private String url;

        public Paste(String code) {
            this.code = code;
        }
        
        private native void console(String message) /*-{
            console.log(message);
        }-*/;
    
        private native String getProperty(JavaScriptObject jso, String property) /*-{
            return jso[property];
        }-*/;

        private String upload() {
            RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, POST_URL);
            try {
                Request response = builder.sendRequest(
                        "{ \"files\" :{"
                            + "\"file1.txt\": {"
                                + "\"content\": \"" + code + "\""
                            + "}"
                        + "}}",
                        new RequestCallback() {
                    public void onError(Request request, Throwable exception) {
                        throw new RuntimeException("Error retrieving from gist.github.com");
                    }
                    public void onResponseReceived(Request request, Response response) {
                        if (!JsonUtils.safeToEval(response.getText())) {
                            throw new RuntimeException("gist file is invalid");
                        }
                        JavaScriptObject jso = JsonUtils.safeEval(response.getText());
                        url = getProperty(jso, "html_url");
                        if (url.lastIndexOf("/") == -1) {
                            throw new RuntimeException("shit happened with gist");
                        }
                        url = url.substring(url.lastIndexOf("/") + 1);
                        console(url);
                    }
                });
            } catch (RequestException e) {
                throw new RuntimeException("Error while getting gist or whatever");
            }
            return url;

        }

    }

}
