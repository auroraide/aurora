package aurora.backend.encoders;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;

import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.tree.Term;
import aurora.backend.library.Library;
import aurora.backend.encoders.exceptions.DecodeException;

/**
 * Serialize/deserialize a {@link Session} into a JSON string.
 */
public class JSONSessionEncoder extends SessionEncoder {

    private final LambdaLexer lambdaLexer;
    private final LambdaParser lambdaParser;

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
        session.library.forEach(item -> lib.push(addLibraryEntry(item.getName(), item.getDescription(), item.getTerm())));
        setProperty(jso, "library", lib);

        return JsonUtils.stringify(jso);
    }

    @Override
    public Session decode(String encodedInput) throws DecodeException {
        if (!JsonUtils.safeToEval(encodedInput)) {
            throw new DecodeException("Invalid json file");
        }
        JavaScriptObject jso = JsonUtils.safeEval(encodedInput);
        String rawInput = (String) getProperty(jso, "rawInput");
        Term inputTerm;
        try {
            inputTerm = lambdaParser.parse(lambdaLexer.lex(rawInput));
        } catch (SemanticException | SyntaxException jje) {
            throw new DecodeException("Invalid json file");
        } 

        return null;
    }

    private native void setProperty(JavaScriptObject jso, String property, Object value) /*-{
        jso[property] = value;
    }-*/;

    private native JsArrayMixed addLibraryEntry(String name, String description, Term term) /*-{
        return [name, description, term];
    }-*/;

    private native Object getProperty(JavaScriptObject jso, String property) /*-{
        return jso[property];
    }-*/;

    private native void console(String message) /*-{
        console.log(message);
    }-*/;


}

