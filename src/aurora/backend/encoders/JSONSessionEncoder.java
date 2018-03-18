package aurora.backend.encoders;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;

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
        JavaScriptObject jso = JavaScriptObject.createObject();
        setProperty(jso, "rawInput", session.rawInput);

        JsArrayMixed lib = JavaScriptObject.createArray().cast();
        session.library.forEach(
                item -> lib.push(
                    addLibraryEntry(
                        item.getName(),
                        item.getDescription(),
                        new HighlightableLambdaExpression(item.getTerm()).toString())));
        setProperty(jso, "library", lib);

        return JsonUtils.stringify(jso);
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
        String toDecode = JsonUtils.escapeJsonForEval(encodedInput);
        if (!JsonUtils.safeToEval(encodedInput)) {
            throw new DecodeException("Invalid json file");
        }
        JavaScriptObject jso = JsonUtils.safeEval(encodedInput);
        //TODO Try catch rawInput
        String rawInput = getProperty(jso, "rawInput");
        //TODO Try catch this one
        String[][] libraryString = getLibrary(jso);
        
        String name;
        String description;
        Term term;

        for (int i = 0; i < libraryString.length; ++i) {
            name = libraryString[i][0];
            description = libraryString[i][1];
            try {
                term = lambdaParser.parse(lambdaLexer.lex(libraryString[i][2]));
            } catch (SemanticException | SyntaxException e) {
                throw new DecodeException("Invalid json file: " + e.getMessage());
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

    private native void setProperty(JavaScriptObject jso, String property, Object value) /*-{
        jso[property] = value;
    }-*/;

    private native JsArrayMixed addLibraryEntry(String name, String description, Term term) /*-{
        return [name, description, term];
    }-*/;

    private native JsArrayMixed addLibraryEntry(String name, String description, String term) /*-{
        return [name, description, term];
    }-*/;

    private native String getProperty(JavaScriptObject jso, String property) /*-{
        return jso[property];
    }-*/;

    private native String[][] getLibrary(JavaScriptObject jso) /*-{
        return jso["library"];
    }-*/;

    private native void readLibrary(JavaScriptObject jso, Library library) /*-{

    }-*/;

    private native void console(String message) /*-{
        console.log(message);
    }-*/;

}

