package aurora.client.presenter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import aurora.client.event.StepEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwtmockito.GwtMockitoTestRunner;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GwtMockitoTestRunner.class)
public class EditorPresenterTest {

    @Test
    public void testStep() throws SyntaxException, SemanticException {
        EditorDisplay editorDisplay = mock(EditorDisplay.class);
        EventBus bus = new SimpleEventBus();

        LambdaParser parserMock = mock(LambdaParser.class);
        LambdaLexer dumbLexer = mock(LambdaLexer.class);
        when(parserMock.parse(any())).thenReturn(getSample());
        ArrayList<Term> stepsForDI = new ArrayList<>();

        EditorPresenter editorPresenter = new EditorPresenter(bus, editorDisplay,
                new HashLibrary(), new HashLibrary(), stepsForDI, dumbLexer, parserMock);

        bus.fireEvent(new StepEvent());

        // make sure the input gets set
        verify(editorDisplay).setInput(new HighlightableLambdaExpression(getSample()));
        ReductionStrategy strategy = new NormalOrder();
        BetaReducer br = new BetaReducer(strategy);
        List<HighlightedLambdaExpression> steps = new ArrayList<>();
        Term last = getSample();
        last = br.reduce(last);
        steps.add(new HighlightableLambdaExpression(last));
        verify(editorDisplay).addNextStep(steps, 1);
    }

    private MetaTerm getSample() {
        Term root = new Application(
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
        return new MetaTerm(root, null);
    }
}
