package aurora.client.presenter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.HighlightedLambdaExpression;
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
import aurora.client.event.StepEventHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import java.util.ArrayList;
import java.util.List;

@RunWith(GwtMockitoTestRunner.class)
public class EditorPresenterTest {
    private static EditorDisplay editorDisplay;
    private EventBus bus;
    private Application root;
    private ReductionStrategy strategy;

    @Test
    public void testStep() throws SyntaxException, SemanticException {
        EditorDisplay editorDisplay = mock(EditorDisplay.class);
        ReductionStrategy strategy = new NormalOrder();
        EventBus bus = new SimpleEventBus();

        LambdaParser parserMock = mock(LambdaParser.class);
        LambdaLexer dumbLexer = mock(LambdaLexer.class);
        when(parserMock.parse(any())).thenReturn(getSample());

        EditorPresenter editorPresenter = new EditorPresenter(bus, editorDisplay, dumbLexer, parserMock);

        final int stepCount = 1;

        bus.fireEvent(new StepEvent(stepCount));

        // make sure the input gets set
        verify(editorDisplay).setInput(new HighlightableLambdaExpression(root));
        BetaReducer br = new BetaReducer(strategy);
        List<HighlightedLambdaExpression> steps = new ArrayList<>();
        Term last = getSample();
        for (int i = 0; i < stepCount; i++) {
            last = br.reduce(last);
            steps.add(new HighlightableLambdaExpression(last));
        }
        verify(editorDisplay).addNextStep(steps);
    }

    private MetaTerm getSample() {
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
        return new MetaTerm(root, null);
    }
}
