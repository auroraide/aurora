package aurora.client.presenter;

import aurora.backend.library.HashLibrary;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.client.SidebarDisplay;
import aurora.client.event.AddFunctionEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(GwtMockitoTestRunner.class)
public class SidebarPresenterTest {
    private static LambdaParser parser;
    private static LambdaLexer lexer;

    private EventBus bus;
    private SidebarDisplay sidebarDisplay;
    private SidebarPresenter sidebarPresenter;

    @BeforeClass
    public static void setUpClass() {
        lexer = new LambdaLexer();
        parser = new LambdaParser(new HashLibrary());
    }

    @Before
    public void setUp() {
        sidebarDisplay = mock(SidebarDisplay.class);
        bus = new SimpleEventBus();
        sidebarPresenter = new SidebarPresenter(
                bus,
                sidebarDisplay,
                new HashLibrary(),
                new HashLibrary(),
                lexer,
                parser
        );
    }

    @Test
    public void addingDollarPrefixFunctionNameInUserLibraryRegressionTest() {
        bus.fireEvent(new AddFunctionEvent("$plus a b", "x", "asdf"));
        verify(sidebarDisplay).displayAddLibraryItemInvalidName();
    }
}
