package aurora.client.presenter;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.encoders.GistEncoder;
import aurora.backend.encoders.Session;
import aurora.backend.library.Library;
import aurora.backend.library.LibraryItem;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.Token;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.Term;
import aurora.client.SidebarDisplay;
import aurora.client.event.AddFunctionEvent;
import aurora.client.event.DeleteFunctionEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import javafx.geometry.Side;

import java.util.List;


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
    private Library userLib;
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
        functionName = RegExp.compile("^([A-Za-z][A-Za-z0-9_]*)");
        this.eventBus = eventBus;
        this.sidebarDisplay = sidebarDisplay;
        this.stdLib = stdLib;
        this.userLib = userLib;
        this.lambdaLexer = lambdaLexer;
        this.lambdaParser = lambdaParser;
        loadUserLibFromURL();
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

        List<Token> tokens;
        String name = input.getName();
        try {
            if (name.charAt(0) == '$') {
                name = name.substring(1);
            }
            tokens = lambdaLexer.lex("$" + name);
        } catch (SyntaxException ex) {
            sidebarDisplay.displayAddLibraryItemSyntaxError(ex);
            return;
        }
        if (tokens.size() != 1 || tokens.get(0).getType() != Token.TokenType.T_FUNCTION) {
            sidebarDisplay.displayAddLibraryItemInvalidName();
            return;
        }

        if (userLib.exists(name) || stdLib.exists(name)) {
            sidebarDisplay.displayAddLibraryItemNameAlreadyTaken();
            GWT.log("Name is already taken.");
            return;
        }

        userLib.define(name, input.getDescription(), t);
        sidebarDisplay.addUserLibraryItem(name, input.getDescription());
        sidebarDisplay.closeAddLibraryItemDialog();

        // TODO Only for debug reasons here. Should be deleted at some time.
        HighlightedLambdaExpression hle = new HighlightableLambdaExpression(t);
        GWT.log("Succesfully parsing lambda term. Parsed Lambda Term:" + hle.toString());
    }

    private void loadUserLibFromURL() {
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
                sidebarDisplay.displayError("Session restore failed.");
            }

            @Override
            public void onSuccess(Session result) {
                SidebarPresenter.this.userLib = result.library;

                for (LibraryItem item : userLib) {
                    SidebarPresenter.this.sidebarDisplay.addUserLibraryItem(item.getName(), item.getDescription());
                }
            }
        });

    }

}
