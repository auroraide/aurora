package aurora.backend.encoders;

import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.tree.Term;
import aurora.backend.library.Library;
import aurora.backend.library.HashLibrary;
import aurora.backend.library.MultiLibrary;
import aurora.backend.encoders.exceptions.DecodeException;
import aurora.backend.encoders.JSONEscaper;
import aurora.backend.parser.Token;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Serialize/deserialize a {@link Session} into a JSON string.
 */
public class JSONSessionEncoder extends SessionEncoder {

    private final Library stdLib;
    private final Library userLib;
    private final LambdaLexer lambdaLexer;
    private final LambdaParser lambdaParser;

    /**
     * Creates a new JSONSessionEncoder, useful for encoding.
     */
    public JSONSessionEncoder() {
        super();
        this.stdLib = null;
        this.userLib = null;
        this.lambdaLexer = null;
        this.lambdaParser = null;
    }

    /**
     * Creates a new JSONSessionEncoder, useful for decoding.
     *
     * @param stdLib the default library.
     */
    public JSONSessionEncoder(Library stdLib) {
        super();
        this.stdLib = stdLib;
        this.userLib = new HashLibrary();
        this.lambdaLexer = new LambdaLexer();
        this.lambdaParser = new LambdaParser(new MultiLibrary(this.stdLib, this.userLib));
    }

    @Override
    public String encode(Session session) {
        JSONObject jo = new JSONObject();
        jo.put("rawInput", new JSONString(session.rawInput));

        JSONArray library = new JSONArray();
        session.library.forEach(
                item -> {
                    JSONArray libraryItem = new JSONArray();
                    libraryItem.set(0, new JSONString(item.getName()));
                    libraryItem.set(1, new JSONString(item.getDescription()));
                    libraryItem.set(2, new JSONString(
                                new HighlightableLambdaExpression(item.getTerm()).toString()));
                    library.set(library.size(), libraryItem);
                });
        jo.put("library", library);
        return jo.toString();
    }

    /**
     * Returns an escaped JSON String.
     *
     * @param toEscape String to escape.
     * @return escaped String.
     */   
    public String escape(String toEscape) {
        JSONEscaper jsonEscaper = new JSONEscaper();
        return jsonEscaper.escape(toEscape);
    }

    @Override
    public Session decode(String encodedInput) throws DecodeException {
        JSONObject value = null;
        String rawInput = null;
        JSONArray encodedLibrary = null;
        Library userLin = new HashLibrary();

        try {
            value = JSONParser.parseStrict(encodedInput).isObject();
        } catch (JSONException e) {
            throw new DecodeException(e.getMessage());
        }

        try {
            rawInput = value.get("rawInput").toString(); 
        } catch (JSONException e) {
            throw new DecodeException(e.getMessage());
        }

        if (rawInput != null && rawInput.length() >= 2) {
            rawInput = rawInput.substring(1, rawInput.length() - 1);
        }

        try {
            encodedLibrary = (JSONArray) value.get("library").isArray();
        } catch (JSONException e) {
            throw new DecodeException(e.getMessage());
        }

        for (int i = 0; i < encodedLibrary.size(); ++i) {
            JSONArray userLibData = (JSONArray) encodedLibrary.get(i);
            final String name = userLibData.get(0).isString().stringValue();
            final String function = userLibData.get(2).isString().stringValue();
            final String description = userLibData.get(1).isString().stringValue();

            Term term;
            try {
                term = lambdaParser.parse(lambdaLexer.lex(function));
            } catch (SyntaxException | SemanticException e) {
                throw new DecodeException(e.getMessage());
            }

            List<Token> tokens;
            try {
                tokens = lambdaLexer.lex("$" + name);
            } catch (SyntaxException e) {
                throw new DecodeException(e.getMessage());
            }
            if (tokens.size() != 1 || tokens.get(0).getType() != Token.TokenType.T_FUNCTION) {
                throw new DecodeException(name + "is invalid function name");
            }

            if ((new MultiLibrary(stdLib, userLib).exists(name))) {
                throw new DecodeException(name + "may only exist once");
            }

            userLib.define(name, description, term);
        }

        return new Session(rawInput, userLib);
    }

    /**
     * Returns an unescapes JSON String.
     *
     * @param toUnescape String to unescape.
     * @return unescaped String.
     */
    public String unescape(String toUnescape) {
        JSONEscaper jsonEscaper = new JSONEscaper();
        return jsonEscaper.unescape(toUnescape);
    }

    //private native void setProperty(JavaScriptObject jso, String property, Object value) /*-{
    //    jso[property] = value;
    //}-*/;

    //private native JsArrayMixed addLibraryEntry(String name, String description, Term term) /*-{
    //    return [name, description, term];
    //}-*/;

    //private native JsArrayMixed addLibraryEntry(String name, String description, String term) /*-{
    //    return [name, description, term];
    //}-*/;

    //private native String getProperty(JavaScriptObject jso, String property) /*-{
    //    return jso[property];
    //}-*/;

    //private native String[][] getLibrary(JavaScriptObject jso) /*-{
    //    return jso["library"];
    //}-*/;

    //private native void readLibrary(JavaScriptObject jso, Library library) /*-{

    //}-*/;

    //private native void console(Object message) /*-{
    //    console.log(message);
    //}-*/;

}

