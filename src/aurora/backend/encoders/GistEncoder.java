package aurora.backend.encoders;

import aurora.backend.encoders.exceptions.DecodeException;
import aurora.backend.library.Library;
import aurora.backend.encoders.JSONSessionEncoder;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;

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
    
    private final LambdaLexer lambdaLexer;
    private final LambdaParser lambdaParser;

    /**
     * Create a new GistEncoder.
     */
    public GistEncoder() {
        this.lambdaLexer = null;
        this.lambdaParser = null;
    }

    /**
     * Decoding requires both lexer and parser.
     *
     * @param lambdaLexer The lambdaLexer.
     * @param lambdaParser The lambdaParser.
     */
    public GistEncoder(LambdaLexer lambdaLexer, LambdaParser lambdaParser) {
        this.lambdaLexer = lambdaLexer;
        this.lambdaParser = lambdaParser;
    }

    public void encode(Session session, AsyncCallback<String> callback) {
        JSONSessionEncoder jse = new JSONSessionEncoder();
        String encodedString = jse.encode(session);
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

    public void decode(String url, AsyncCallback<String> callback) {
        JSONSessionEncoder jse = new JSONSessionEncoder(lambdaLexer, lambdaParser);
        getGist(url, callback);
    }

    private void postGist(String encodedString, AsyncCallback<String> callback) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, GIST_URL);
        try {
            Request response = builder.sendRequest(
                    "{ \"files\" :{"
                    + "\"" + FILE_NAME + "\": {"
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
                            JSONSessionEncoder jse = new JSONSessionEncoder(lambdaLexer, lambdaParser);
                            callback.onSuccess(url);
                        }
                    });
        } catch (RequestException e) {
            //TODO: do something od Failure?
            throw new RuntimeException("Error while getting gist or whatever");
        }
    }

    private void getGist(String url, AsyncCallback<String> callback) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, GIST_URL + "/" + url);
        try {
            Request response = builder.sendRequest("", new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    throw new RuntimeException("Error retrieving from gist.github.com");
                }

                public void onResponseReceived(Request request, Response response) {
                    if (!JsonUtils.safeToEval(response.getText())) {
                        throw new RuntimeException("gist url is not a valid json");
                    }
                    JavaScriptObject jso = JsonUtils.safeEval(response.getText());
                    callback.onSuccess(getProperty(jso, "files", FILE_NAME, "content"));
                }

                private native void console(String message) /*-{
                    console.log(message);
                }-*/;
            });
        } catch (RequestException e) {
            //TODO do something onFailure?
            throw new RuntimeException("Error while getting json from url");
        }
    }

    private native String getProperty(JavaScriptObject jso, String property) /*-{
        return jso[property].toString();
    }-*/;

    private native String getProperty(JavaScriptObject jso, String prop1, String prop2, String prop3) /*-{
        return jso[prop1][prop2][prop3];
    }-*/;

}

