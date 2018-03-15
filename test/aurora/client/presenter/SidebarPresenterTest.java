package aurora.client.presenter;

import aurora.backend.library.HashLibrary;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.simplifier.ChurchNumberSimplifier;
import aurora.backend.simplifier.LibraryTermSimplifier;
import aurora.client.EditorDisplay;
import aurora.client.SidebarDisplay;
import aurora.client.event.AddFunctionEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SidebarPresenterTest {
    private static LambdaParser parser;
    private static LambdaLexer lexer;

    private SidebarDisplay sidebarDisplay;
    private EventBus bus;
    private SidebarPresenter sidebarPresenter;

    @BeforeClass
    public static void setUpClass() {
        lexer = new LambdaLexer();
        parser = new LambdaParser(new HashLibrary());
    }

    /**
     * Sets up sidebarDisplay, bus, sidebarPresenter.
     */
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
    public void regressionTest178AddingFunctionnameWithWhitespaceWorksBug() {
        bus.fireEvent(new AddFunctionEvent("hello there", "x", "descr"));
        verify(sidebarDisplay).displayAddLibraryItemInvalidName();
    }

    @Test
    public void addingDollarPrefixFunctionNameInUserLibraryRegressionTest() {
        bus.fireEvent(new AddFunctionEvent("$plus a b", "x", "asdf"));
        verify(sidebarDisplay).displayAddLibraryItemInvalidName();
    }
}