package aurora.client;

import aurora.backend.library.HashLibrary;
import aurora.backend.library.Library;
import aurora.backend.library.MultiLibrary;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.simplifier.ChurchNumberSimplifier;
import aurora.backend.simplifier.LibraryTermSimplifier;
import aurora.backend.tree.Term;
import aurora.client.presenter.AuroraPresenter;
import aurora.client.presenter.EditorPresenter;
import aurora.client.presenter.SidebarPresenter;
import aurora.client.view.AuroraView;
import aurora.client.view.editor.EditorView;
import aurora.client.view.sidebar.SidebarView;
import aurora.resources.Resources;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.debug.client.DebugInfo;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import java.util.ArrayList;

/**
 * Responsible for intitalising the Aurora Web Application.
 * <p>
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Aurora implements EntryPoint {

    /**
     * This is the entry point method. Sets up and initialises the Aurora Web Application.
     */
    public void onModuleLoad() {
        // Remove the need to specify a debugId prefix.
        DebugInfo.setDebugIdPrefix("");

        EventBus eventBus = GWT.create(SimpleEventBus.class);

        // views/displays, ordering is important
        SidebarView sidebarView = new SidebarView(eventBus);
        EditorView editorView = new EditorView(eventBus);
        AuroraView auroraView = new AuroraView(eventBus, sidebarView, editorView);

        // libraries
        Library userLib = new HashLibrary();
        Library stdLib = new HashLibrary();
        readInStdLibFunctions(stdLib);

        ArrayList<Term> steps = new ArrayList<>();

        Library library = new MultiLibrary(userLib, stdLib);

        LambdaLexer lexer = new LambdaLexer();
        LambdaParser parser = new LambdaParser(library);

        // presenters
        AuroraPresenter auroraPresenter = new AuroraPresenter(
                eventBus,
                auroraView,
                steps);

        SidebarPresenter sidebarPresenter = new SidebarPresenter(
                eventBus,
                auroraView.getSidebar(),
                stdLib,
                userLib,
                lexer,
                parser);

        EditorPresenter editorPresenter = new EditorPresenter(
                eventBus,
                auroraView.getEditor(),
                stdLib,
                userLib,
                new ChurchNumberSimplifier(),
                new LibraryTermSimplifier(library),
                steps,
                lexer,
                parser);

        RootLayoutPanel.get().add(auroraView);
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private void readInStdLibFunctions(Library stdLib) {
        final String errorMessage = "Failed initialising the standard library";
        String json = Resources.INSTANCE.stdlibFunctionData().getText();
        JSONValue value;
        try {
            value = JSONParser.parseStrict(json);
        } catch (JSONException e) {
            GWT.log(errorMessage);
            return;
        }

        JSONArray stdlibFunctionArray = (JSONArray) value;
        Term t;

        LambdaLexer lambdaLexer = new LambdaLexer();
        LambdaParser lambdaParser = new LambdaParser(stdLib);

        for (int i = 0; i < stdlibFunctionArray.size(); i++) {
            JSONObject stdlibFunctionData = (JSONObject) stdlibFunctionArray.get(i);

            final String name = stdlibFunctionData.get("name").isString().stringValue();
            final String function = stdlibFunctionData.get("function").isString().stringValue();
            final String description = stdlibFunctionData.get("description").isString().stringValue();

            try {
                t = lambdaParser.parse(lambdaLexer.lex(function));
            } catch (SyntaxException e) {
                GWT.log("Syntax Exception! Failed to lex " + "[" + function + "]" + "of function " + name + ".");
                return;
            } catch (SemanticException e) {
                GWT.log("Semantic Exception! Failed to lex " + "[" + function + "]" + "of function " + name + ".");
                return;
            }

            final RegExp functionName = RegExp.compile("^([A-Za-z][A-Za-z0-9_]*)");

            MatchResult result = functionName.exec(name);
            if (result == null || isNullOrEmpty(result.getGroup(0))) {
                GWT.log(name + " is an invalid function name!");
                return;
            }

            if (stdLib.exists(name)) {
                GWT.log("Function name " + name + "is already taken!");
                return;
            }

            stdLib.define(name, description, t);
        }
    }
}
