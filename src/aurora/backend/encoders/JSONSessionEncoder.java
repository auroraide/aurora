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
import aurora.backend.encoders.exceptions.DecodeException;

/**
 * Serialize/deserialize a {@link Session} into a JSON string.
 */
public class JSONSessionEncoder extends SessionEncoder {

    private final LambdaLexer lambdaLexer;
    private final LambdaParser lambdaParser;

    /**
     * Neither lexer nor parser are needed to encode to JSON.
     */
    public JSONSessionEncoder() {
        super();
        this.lambdaLexer = null;
        this.lambdaParser = null;
    }

    /**
     * Decoding requires both lexer and parser.
     *
     * @param lambdaLexer The lambdaLexer.
     * @param lambdaParser The LambdaParser.
     */
    public JSONSessionEncoder(LambdaLexer lambdaLexer, LambdaParser lambdaParser) {
        super();
        this.lambdaLexer = lambdaLexer;
        this.lambdaParser = lambdaParser;
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

    @Override
    public Session decode(String encodedInput) throws DecodeException {
        if (!JsonUtils.safeToEval(encodedInput)) {
            throw new DecodeException("Invalid json file");
        }
        JavaScriptObject jso = JsonUtils.safeEval(encodedInput);
        //TODO Try catch rawInput
        String rawInput = getProperty(jso, "rawInput");
        //TODO Try catch this one
        String[][] libraryString = getLibrary(jso);
        Library library = new HashLibrary();
        
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
            library.define(name, description, term);
        }

        return new Session(rawInput, library);
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

