package aurora.client.view.sidebar;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.testing.CountingEventBus;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.ArrayList;
import java.util.List;


/**
 * Testing {@link SidebarView}.
 */
public class SidebarViewTest extends GWTTestCase {
    private SidebarView sidebarView;
    private CountingEventBus eventBus;

    private static native String getNodeName(Element elem) /*-{
        return (elem.nodeName || "").toLowerCase();
    }-*/;

    public String getModuleName() {
        return "aurora.Aurora";
    }


    /**
     * Removes all elements in the body, except scripts and iframes.
     */
    public void gwtSetUp() {
        Element bodyElem = RootPanel.getBodyElement();

        List<Element> toRemove = new ArrayList<Element>();
        for (int i = 0, n = DOM.getChildCount(bodyElem); i < n; ++i) {
            Element elem = DOM.getChild(bodyElem, i);
            String nodeName = getNodeName(elem);
            if (!"script".equals(nodeName) && !"iframe".equals(nodeName)) {
                toRemove.add(elem);
            }
        }

        for (int i = 0, n = toRemove.size(); i < n; ++i) {
            bodyElem.removeChild(toRemove.get(i));
        }
    }


    /**
     * Tests if {@link SidebarView} is intialised with the correct values.
     */
    public void testSidebarInitialize() {
        SidebarView sidebarView = new SidebarView(eventBus);

        // stepNumber should be initialised with value 1
        assertEquals("1", sidebarView.stepNumber.getText());

        // check, if NormalOrder is selected on initialisation
        assertTrue(sidebarView.strategySelection.getNormalOrder().getValue());
        assertFalse(sidebarView.strategySelection.getCallByName().getValue());
        assertFalse(sidebarView.strategySelection.getCallByValue().getValue());
        assertFalse(sidebarView.strategySelection.getManualSelection().getValue());

        // check, if nighMode is disabled on initialisation
        assertFalse(sidebarView.nightModeSwitch.getValue());
    }

}
