package aurora.backend.encoders;

import aurora.backend.encoders.exceptions.DecodeException;
import aurora.backend.library.Library;
import aurora.backend.encoders.JSONSessionEncoder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;

/**
 * GIST Encoder and Decoder.
 * Saves the current input and library items in a GIST, and returns the URL.
 * Given a valid URL, it returns input as well as the decoded library items.
 */
public class GistEncoder {

    private static final String GIST_URL = "https://api.github.com/gists";
    private static final String FILE_NAME = "aurora.txt";

    private static final String ERROR_RETRIEVING = "Error receiving an answer from gist.github.com";
    private static final String REQUEST_FAILED = "The request failed, maybe you exceeded your gist api limit?";
    private static final String INVALID_ANSWER = "The received answer is invalid.";
    
    private final Library stdLibrary;

    /**
     * Create a new GistEncoder.
     */
    public GistEncoder() {
        this.stdLibrary = null;
    }

    /**
     * Create a new GistEncoder, useful for decoding.
     *
     * @param stdLib which base library to use.
     */
    public GistEncoder(Library stdLib) {
        this.stdLibrary = stdLib;
    }

    /**
     * Encodes a given Session.
     *
     * @param session The session to be encoded.
     * @param callback What to do on success/failuer.
     */
    public void encode(Session session, AsyncCallback<String> callback) {
        JSONSessionEncoder jse = new JSONSessionEncoder();
        String encodedString = jse.encode(session);
        encodedString = jse.escape(encodedString);
        postGist(encodedString, callback);
    }

    /**
     * Encodes a given input along with the library.
     *
     * @param rawInput The raw input to be encoded.
     * @param library The library object to be encoded.
     * @param callback What to do on success/failuer.
     */
    public void encode(String rawInput, Library library, AsyncCallback<String> callback) {
        encode(new Session(rawInput, library), callback);
    }

    public void decode(String url, AsyncCallback<Session> callback) {
        JSONSessionEncoder jse = new JSONSessionEncoder(stdLibrary);
        getGist(url, callback);
    }

    private void postGist(String encodedString, AsyncCallback<String> callback) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, GIST_URL);
        try {
            Request response = builder.sendRequest(
                    "{ \"files\" :{"
                    + "\"" + FILE_NAME + "\": {"
                    + "\"content\": \"" + encodedString + "\""
                    + "}"
                    + "}}",
                    new RequestCallback() {
                        public void onError(Request request, Throwable exception) {
                            throw new RuntimeException(ERROR_RETRIEVING);
                        }

                        public void onResponseReceived(Request request, Response response) {
                            if (response.getStatusCode() != 201) {
                                throw new RuntimeException(REQUEST_FAILED); 
                            }
                            if (!JsonUtils.safeToEval(response.getText())) {
                                throw new RuntimeException(REQUEST_FAILED);
                            }
                            JavaScriptObject jso = JsonUtils.safeEval(response.getText());
                            String url = getProperty(jso, "html_url");
                            if (url.lastIndexOf("/") == -1) {
                                throw new RuntimeException(INVALID_ANSWER);
                            }
                            url = url.substring(url.lastIndexOf("/") + 1);
                            callback.onSuccess(url);
                        }
                    });
        } catch (RequestException e) {
            //TODO: do something od Failure?
            throw new RuntimeException(ERROR_RETRIEVING);
        }
    }

    private void getGist(String url, AsyncCallback<Session> callback) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, GIST_URL + "/" + url);
        try {
            Request response = builder.sendRequest("", new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    throw new RuntimeException(ERROR_RETRIEVING);
                }

                public void onResponseReceived(Request request, Response response) {

                    if (response.getStatusCode() != 200) {
                        throw new RuntimeException(REQUEST_FAILED); 
                    }
                    if (!JsonUtils.safeToEval(response.getText())) {
                        throw new RuntimeException(INVALID_ANSWER);
                    }

                    GWT.log(response.getText());
                    JavaScriptObject jso = JsonUtils.safeEval(response.getText());
                    JSONSessionEncoder jse = new JSONSessionEncoder(stdLibrary);
                    String json = getProperty(jso, "files", FILE_NAME, "content");
                    Session session;
                    try {
                        session = jse.decode(json);
                    } catch (DecodeException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                    callback.onSuccess(session);
                }

            });
        } catch (RequestException e) {
            //TODO do something onFailure?
            throw new RuntimeException(ERROR_RETRIEVING);
        }
    }

    private native String getProperty(JavaScriptObject jso, String property) /*-{
        return jso[property];
    }-*/;

    private native String getProperty(JavaScriptObject jso, String prop1, String prop2, String prop3) /*-{
        return jso[prop1][prop2][prop3];
    }-*/;

}

