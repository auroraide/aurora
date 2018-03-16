package aurora.backend.encoders;

import aurora.backend.encoders.exceptions.DecodeException;
import aurora.backend.library.Library;
import aurora.backend.encoders.JSONSessionEncoder;

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

    private static final String POST_URL = "https://api.github.com/gists";

    /**
     * Create a new GistEncoder.
     */
    public GistEncoder() {}

    /**
     * Encodes a given input along with the library.
     *
     * @param rawInput The raw input to be encoded.
     * @param library The library object to be encoded.
     * @param callback What to do on success/failuer.
     */
    public void encode(String rawInput, Library library, AsyncCallback<String> callback) {
        JSONSessionEncoder jse = new JSONSessionEncoder();
        String encodedString = jse.encode(rawInput, library);
        postGist(encodedString, callback);
    }

    public void decode(String encodedInput, AsyncCallback<String> callback) throws DecodeException {
        
    }

    private void postGist(String encodedString, AsyncCallback<String> callback) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, POST_URL);
        try {
            Request response = builder.sendRequest(
                    "{ \"files\" :{"
                    + "\"file1.txt\": {"
                    + "\"content\": \"" + encodedString.replaceAll("\"", "\\\\\"") + "\""
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
                            String url = getProperty(jso, "html_url");
                            if (url.lastIndexOf("/") == -1) {
                                throw new RuntimeException("shit happened with gist");
                            }
                            url = url.substring(url.lastIndexOf("/") + 1);
                            callback.onSuccess(url);
                        }
                    });
        } catch (RequestException e) {
            //TODO: do something od Failure?
            throw new RuntimeException("Error while getting gist or whatever");
        }
    }

    private native String getProperty(JavaScriptObject jso, String property) /*-{
        return jso[property];
    }-*/;

}

