package aurora;

import aurora.client.view.editor.EditorViewGwtTest;
import aurora.client.view.popup.ShareDialogBoxGwtTest;
import aurora.client.view.sidebar.SidebarViewGwtTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class GwtTestSuite extends GWTTestSuite {

    /**
     * Creates a GWTTestCase Suite.
     *
     * @return The test.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("Tests for StockWatcher");
        suite.addTestSuite(EditorViewGwtTest.class);
        suite.addTestSuite(ShareDialogBoxGwtTest.class);
        suite.addTestSuite(SidebarViewGwtTest.class);
        return suite;
    }
}
