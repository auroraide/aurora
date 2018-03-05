package aurora.client.presenter;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.library.Library;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.Term;
import aurora.client.SidebarDisplay;
import aurora.client.event.AddFunctionEvent;
import aurora.client.event.DeleteFunctionEvent;
import aurora.resources.Resources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;


/**
 * <code>SidebarPresenter</code> is responsible for the presentation logic.
 * <p>
 * It fetches sidebar specific user events and acts upon those
 * by using the backend, which presents the model. <code>Aurora Presenter</code> then updates the view through
 * via the {@link SidebarDisplay}.
 */
public class SidebarPresenter {
    private final EventBus eventBus;
    private final SidebarDisplay sidebarDisplay;
    private final Library stdLib;
    private final Library userLib;
    private final LambdaLexer lambdaLexer;
    private final LambdaParser lambdaParser;

    private final RegExp functionName;

    /**
     * Creates an <code>EditorPresenter</code> with an {@link EventBus} and a {@link SidebarDisplay}.
     *  @param eventBus       The event bus.
     * @param sidebarDisplay The {@link SidebarDisplay}
     */
    public SidebarPresenter(EventBus eventBus,
                            SidebarDisplay sidebarDisplay,
                            Library stdLib,
                            Library userLib,
                            LambdaLexer lambdaLexer,
                            LambdaParser lambdaParser) {
        this.eventBus = eventBus;
        this.sidebarDisplay = sidebarDisplay;
        this.stdLib = stdLib;
        this.userLib = userLib;
        this.lambdaLexer = lambdaLexer;
        this.lambdaParser = lambdaParser;
        bind();
        functionName = RegExp.compile("^[a-z]+$");
        readInStdLibFunctions();
    }

    private void bind() {
        eventBus.addHandler(AddFunctionEvent.TYPE, this::onAddFunction);
        eventBus.addHandler(DeleteFunctionEvent.TYPE, this::onDeletFunction);
    }

    private void onDeletFunction(DeleteFunctionEvent e) {
        userLib.remove(e.getFunctionName());
        GWT.log("Delete userllib function.");
    }

    private void onAddFunction(AddFunctionEvent input) {
        // TODO remove logs. Only for debug reasons here.
        Term t;
        try {
            GWT.log("Begin parsing...");
            t = lambdaParser.parse(lambdaLexer.lex(input.getLambdaTerm()));
            GWT.log("parsing...");
        } catch (SyntaxException e) {
            sidebarDisplay.displayAddLibraryItemSyntaxError(e);
            GWT.log("syntax exception thrown while parsing lambda term.");
            return;
        } catch (SemanticException e) {
            sidebarDisplay.displayAddLibraryItemSemanticError(e);
            GWT.log("semantic exception thrown while parsing lambda term.");
            return;
        }

        MatchResult result = functionName.exec(input.getName());
        String resultString = result.getGroup(0);
        if (resultString == null || resultString.isEmpty()) {
            sidebarDisplay.displayAddLibraryItemInvalidName();
            return;
        }

        if (userLib.exists(input.getName()) || stdLib.exists(input.getName())) {
            sidebarDisplay.displayAddLibraryItemNameAlreadyTaken();
            GWT.log("Name is already taken.");
            return;
        }

        userLib.define(input.getName(), input.getDescription(), t);
        sidebarDisplay.addUserLibraryItem(input.getName(), input.getDescription());
        sidebarDisplay.closeAddLibraryItemDialog();

        // TODO Only for debug reasons here. Should be deleted at some time.
        HighlightedLambdaExpression hle = new HighlightableLambdaExpression(t);
        GWT.log("Succesfully parsing lambda term. Parsed Lambda Term:" + hle.toString());
    }

    private void readInStdLibFunctions() {
        String json = Resources.INSTANCE.stdlibFunctionData().getText();
        JSONValue value = JSONParser.parseStrict(json);
        JSONArray stdlibFunctionArray = (JSONArray) value;
        Term t;

        for (int i = 0; i < stdlibFunctionArray.size(); i++) {
            JSONObject stdlibFunctionData = (JSONObject) stdlibFunctionArray.get(i);

            String name =  stdlibFunctionData.get("name").isString().stringValue();
            GWT.log(name);

            String function = stdlibFunctionData.get("function").isString().stringValue();
            GWT.log(function);

            String description = stdlibFunctionData.get("description").isString().stringValue();
            GWT.log(description);

            try {
                t = lambdaParser.parse(lambdaLexer.lex(function));
            } catch (SyntaxException e) {
                GWT.log("Syntax Exception! Failed to lex " + "[" + function + "]" + "of function " + name + "!");
                return;
            } catch (SemanticException e) {
                GWT.log("Semantic Exception! Failed to lex " + "[" + function + "]" + "of function " + name + "!");
                return;
            }

            /*MatchResult result = functionName.exec(name);
            String resultString = result.getGroup(0);
            if (resultString == null || resultString.isEmpty()) {
                GWT.log(name + " is an invalid function name!");
                return;
            }

            if (stdLib.exists(name)) {
                sidebarDisplay.displayAddLibraryItemNameAlreadyTaken();
                GWT.log("Function name " + name + "is already taken!");
                return;
            }*/

            stdLib.define(name, description, t);
            sidebarDisplay.addStandardLibraryItem(name, description);
        }

    }
}
