package aurora.client.presenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import aurora.backend.Comparer;
import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.MetaTerm;
import aurora.backend.betareduction.BetaReducer;
import aurora.backend.betareduction.strategies.NormalOrder;
import aurora.backend.betareduction.strategies.ReductionStrategy;
import aurora.backend.library.HashLibrary;
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
import aurora.client.event.ResetEvent;
import aurora.client.event.RunEvent;
import aurora.client.event.StepEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwtmockito.GwtMockitoTestRunner;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GwtMockitoTestRunner.class)
public class EditorPresenterTest {
    private static LambdaParser parser;
    private static LambdaLexer lexer;

    private EditorDisplay editorDisplay;
    private EventBus bus;
    private EditorPresenter editorPresenter;

    @BeforeClass
    public static void setUpClass() {
        lexer = new LambdaLexer();
        parser = new LambdaParser(new HashLibrary());
    }

    @Before
    public void setUp() {
        editorDisplay = mock(EditorDisplay.class);
        bus = new SimpleEventBus();
    }

    private void setUpPresenterWithInput(String code) {
        when(editorDisplay.getInput()).thenReturn(code);
        editorPresenter = new EditorPresenter(bus, editorDisplay, new HashLibrary(), new HashLibrary(),
                new ArrayList<>(), lexer, parser);
    }

    private Term parse(String code) throws SyntaxException, SemanticException {
        return parser.parse(lexer.lex(code));
    }

    private void assertAlphaEquivalent(Term expected, Term actual) {
        Comparer cmp = new Comparer(expected, actual);
        assertTrue(cmp.compare());
    }

    private void assertAlphaEquivalent(String expected, Term actual) throws SyntaxException, SemanticException {
        assertAlphaEquivalent(parse(expected), actual);
    }

    @Test
    public void testStep() throws SyntaxException, SemanticException {
        String sampleString = "(\\x. x x a) \\y. y y b";
        setUpPresenterWithInput(sampleString);
        Term sample = parse(sampleString);

        bus.fireEvent(new StepEvent());

        // make sure the input gets set
        verify(editorDisplay).setInput(new HighlightableLambdaExpression(sample));
        ReductionStrategy strategy = new NormalOrder();
        BetaReducer br = new BetaReducer(strategy);
        List<HighlightedLambdaExpression> steps = new ArrayList<>();
        Term last = sample;
        last = br.reduce(last);
        steps.add(new HighlightableLambdaExpression(last));
        verify(editorDisplay).addNextStep(steps, 1);
    }

    @Test
    public void testResetRunning() {
        String sampleString = "(\\x. x x a) \\y. y y b";
        setUpPresenterWithInput(sampleString);

        bus.fireEvent(new RunEvent());
        verify(editorDisplay).setInput(any());
        bus.fireEvent(new ResetEvent());
        bus.fireEvent(new StepEvent());
    }
}
