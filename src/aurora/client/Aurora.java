package aurora.client;

import aurora.backend.encoders.GistEncoder;
import aurora.backend.encoders.Session;
import aurora.backend.library.HashLibrary;
import aurora.backend.library.Library;
import aurora.backend.library.LibraryItem;
import aurora.backend.library.MultiLibrary;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.Token;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.simplifier.ChurchNumberSimplifier;
import aurora.backend.simplifier.LibraryTermSimplifier;
import aurora.backend.tree.Term;
import aurora.client.presenter.AuroraPresenter;
import aurora.client.presenter.EditorPresenter;
import aurora.client.presenter.SidebarPresenter;
import aurora.client.presenter.Step;
import aurora.client.view.AuroraView;
import aurora.client.view.editor.EditorView;
import aurora.client.view.sidebar.SidebarView;
import aurora.resources.Resources;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.debug.client.DebugInfo;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import java.util.ArrayList;
import java.util.List;

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

        // libraries
        Library userLib = new HashLibrary();
        Library stdLib = new HashLibrary();
        readInStdLibFunctions(stdLib);

        ArrayList<Step> steps = new ArrayList<>();

        Library library = new MultiLibrary(userLib, stdLib);

        LambdaLexer lexer = new LambdaLexer();
        LambdaParser parser = new LambdaParser(library);

        // views/displays, ordering is important
        SidebarView sidebarView = new SidebarView(eventBus);
        EditorView editorView = new EditorView(eventBus);
        AuroraView auroraView = new AuroraView(eventBus, sidebarView, editorView);

        restoreSession(auroraView, editorView, sidebarView, library, stdLib, userLib);
        setupPresenter(eventBus, auroraView, editorView, sidebarView, library, stdLib, userLib, steps, lexer, parser);

    }

    private void setupPresenter(EventBus eventBus, AuroraView auroraView, EditorView editorView,
                                SidebarView sidebarView, Library library, Library stdLib, Library userLib,
                             ArrayList<Step> steps, LambdaLexer lexer, LambdaParser parser) {

        // presenters
        AuroraPresenter auroraPresenter = new AuroraPresenter(
                eventBus,
                auroraView,
                editorView,
                userLib,
                stdLib,
                steps);

        SidebarPresenter sidebarPresenter = new SidebarPresenter(
                eventBus,
                sidebarView,
                stdLib,
                userLib,
                lexer,
                parser);

        EditorPresenter editorPresenter = new EditorPresenter(
                eventBus,
                editorView,
                stdLib,
                userLib,
                new ChurchNumberSimplifier(),
                new LibraryTermSimplifier(library),
                steps,
                lexer,
                parser);

        RootLayoutPanel.get().add(auroraView);

    }

    private void restoreSession(AuroraView auroraView, EditorView editorView, SidebarView sidebarView,
                                Library multiLibrary, Library stdLib, Library userLib) {
        String url = Window.Location.getHref();
        String[] urlSplit;
        String gistCode;


        if (!url.contains("#")) {
            // Nothing to load, therefore stop
            return;
        }

        urlSplit = url.split("#");
        assert (urlSplit.length == 2);

        gistCode = urlSplit[1];

        GistEncoder encoder = new GistEncoder(stdLib);

        encoder.decode(gistCode, new AsyncCallback<Session>() {
            @Override
            public void onFailure(Throwable caught) {
                Scheduler.get().scheduleDeferred((Command) () ->
                        auroraView.displayError("Session restore failed."));
            }

            @Override
            public void onSuccess(Session result) {
                multiLibrary.clear();
                multiLibrary.define(stdLib);
                multiLibrary.define(result.library);

                userLib.clear();
                userLib.define(result.library);

                Scheduler.get().scheduleDeferred((Command) () -> {
                    editorView.setInput(result.rawInput.replaceAll("\\n", ""));
                    sidebarView.clearUserLibrary();

                    for (LibraryItem item : result.library) {
                        sidebarView.addUserLibraryItem(item.getName(), item.getDescription());
                    }
                });

            }

        });


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

            List<Token> tokens;
            try {
                tokens = lambdaLexer.lex("$" + name);
            } catch (SyntaxException ex) {
                GWT.log(name + " is an invalid function name!");
                return;
            }
            if (tokens.size() != 1 || tokens.get(0).getType() != Token.TokenType.T_FUNCTION) {
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
