package aurora.client.presenter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.MetaTerm;
import aurora.backend.betareduction.BetaReducer;
import aurora.backend.betareduction.BetaReductionIterator;
import aurora.backend.betareduction.strategies.NormalOrder;
import aurora.backend.betareduction.strategies.ReductionStrategy;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Term;
import aurora.client.EditorDisplay;
import aurora.client.event.StepEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GwtMockitoTestRunner.class)
public class EditorPresenterTest {
    private static EditorDisplay editorDisplay;
    private EventBus bus;
    private Application root;
    private ReductionStrategy strategy;

    @BeforeClass
    public static void setUpClass() {

    }

    /**
     * Some tests.
     *
     * @throws SyntaxException   An exception.
     * @throws SemanticException Another exception.
     */
    @Before
    public void setUp() throws SyntaxException, SemanticException {
        editorDisplay = mock(EditorDisplay.class);
        strategy = new NormalOrder();
        // RedexPath redexPath = mock(RedexPath.class);
        // when(strategy.getRedex(any())).thenReturn(redexPath); // return empty redexPath

        bus = GWT.create(SimpleEventBus.class);
        LambdaParser parserMock = mock(LambdaParser.class);
        LambdaLexer dumbLexer = mock(LambdaLexer.class);

        root = new Application(
                new Abstraction(
                        new Application(
                                new BoundVariable(1),
                                new Application(
                                        new BoundVariable(1),
                                        new FreeVariable("a")
                                )
                        ),
                        "x"
                ),
                new Abstraction(
                        new Application(
                                new BoundVariable(1),
                                new Application(
                                        new BoundVariable(1),
                                        new FreeVariable("b")
                                )
                        ),
                        "y"
                )
        );
        MetaTerm mt = new MetaTerm(root, null);
        // when(redexPath.get(any())).thenReturn(root);

        when(parserMock.parse(any())).thenReturn(mt);

        new EditorPresenter(bus, null, dumbLexer, parserMock);
    }

    @Test
    public void test() throws SyntaxException, SemanticException {
        when(editorDisplay.getInput()).thenReturn("(\\x.x x a)(\\y.y y y)");

        bus.fireEvent(new StepEvent(1));

        Term unused = new LambdaParser().parse(new LambdaLexer().lex(editorDisplay.getInput()));
        BetaReductionIterator bri = new BetaReductionIterator(new BetaReducer(strategy), root);

        verify(editorDisplay).setInput(new HighlightableLambdaExpression(root));
        verify(editorDisplay).setInput(new HighlightableLambdaExpression(bri.next()));
    }
}
