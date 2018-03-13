package aurora.client.presenter;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.library.Library;
import aurora.backend.library.LibraryItem;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.Term;
import aurora.client.SidebarDisplay;
import aurora.client.event.AddFunctionEvent;
import aurora.client.event.DeleteFunctionEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
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
                            LambdaParser lambdaParser)
    {
        functionName = RegExp.compile("^([A-Za-z][A-Za-z0-9_]*)");
        this.eventBus = eventBus;
        this.sidebarDisplay = sidebarDisplay;
        this.stdLib = stdLib;
        this.userLib = userLib;
        this.lambdaLexer = lambdaLexer;
        this.lambdaParser = lambdaParser;
        bind();
        populateStdLibInView();
    }

    private void populateStdLibInView() {
        for (LibraryItem item : stdLib) {
            sidebarDisplay.addStandardLibraryItem(item.getName(), item.getDescription());
        }
    }

    private void bind() {
        eventBus.addHandler(AddFunctionEvent.TYPE, this::onAddFunction);
        eventBus.addHandler(DeleteFunctionEvent.TYPE, this::onDeleteFunction);
    }

    private void onDeleteFunction(DeleteFunctionEvent e) {
        userLib.remove(e.getFunctionName());
        GWT.log("Delete userlib function.");
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
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
        if (result == null || isNullOrEmpty(result.getGroup(0))) {
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

}
