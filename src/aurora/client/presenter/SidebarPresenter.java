package aurora.client.presenter;

import aurora.backend.library.Library;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.Term;
import aurora.client.SidebarDisplay;
import aurora.client.event.AddFunctionEvent;
import com.google.gwt.event.shared.EventBus;


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
    }

    public SidebarPresenter(EventBus eventBus, SidebarDisplay sidebarDisplay, Library stdLib, Library userLib) {
        this(eventBus, sidebarDisplay, stdLib, userLib, new LambdaLexer(), new LambdaParser());
    }

    private void bind() {
        eventBus.addHandler(AddFunctionEvent.TYPE, this::onAddFunction);
    }

    private void onAddFunction(AddFunctionEvent input) {
        Term t;
        try {
            t = lambdaParser.parse(lambdaLexer.lex(input.getLambdaTerm()));
        } catch (SyntaxException | SemanticException e) {
            throw new RuntimeException("Not implemented");
            // sidebarDisplay.displayAddUserLibraryError(e);
        }

        if (userLib.exists(input.getName())) {
            throw new RuntimeException("Not implemented");
            // sidebarDisplay.displayUserLibraryNameAlreadyTakenError();
        }

        userLib.define(input.getName(), input.getDescription(), t);
        sidebarDisplay.addUserLibraryItem(input.getName(), input.getDescription());
        sidebarDisplay.closeAddLibraryItemDialog();
    }
}
