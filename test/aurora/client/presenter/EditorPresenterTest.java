package aurora.client.presenter;

import aurora.client.EditorDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;

@RunWith(GwtMockitoTestRunner.class)
public class EditorPresenterTest {
    @Test
    public void test() {
        EventBus bus = GWT.create(SimpleEventBus.class);
        EditorDisplay editorDisplay = mock(EditorDisplay.class);
        EditorPresenter ep = new EditorPresenter(bus, null);
    }
}
