package aurora.utils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods to help setup a GWTTestCase.
 */
public class GWTTestCaseSetup {

    /**
     * Cleans up DOM except for scripts and iFrames.
     *
     * @param rootPanel The {@link RootPanel}
     */
    public static void cleanUpDOM(RootPanel rootPanel) {
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

    private static native String getNodeName(Element elem) /*-{
        return (elem.nodeName || "").toLowerCase();
    }-*/;
}
