package aurora.client.view.sidebar;

import aurora.client.event.AddFunctionEvent;
import aurora.client.event.DeleteFunctionEvent;
import aurora.client.event.ViewStateChangedEvent;
import aurora.client.view.ViewState;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.testing.CountingEventBus;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Command;
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

        eventBus = new CountingEventBus();
        sidebarView = new SidebarView(eventBus);
        RootPanel.get().add(sidebarView);
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

    /**
     * Tests if {@link SidebarView}'s widgets are correctly enabled or disabled in DefaultState.
     */
    public void testSidebarDefaultState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.DEFAULT_STATE));
        assertTrue(sidebarView.stepNumber.isEnabled());
        assertTrue(sidebarView.addFunctionButton.isEnabled());
        assertTrue(sidebarView.strategySelection.getCallByValue().isEnabled());
        assertTrue((sidebarView.strategySelection.getCallByName().isEnabled()));
        assertTrue(sidebarView.strategySelection.getManualSelection().isEnabled());
        assertTrue(sidebarView.strategySelection.getNormalOrder().isEnabled());
        assertTrue(sidebarView.nightModeSwitch.isEnabled());
        //TODO Check if share and language button (Sidebar) are enabled in DefaultState
    }

    /**
     * Tests if {@link SidebarView}'s widgets are correctly enabled or disabled in RunningState.
     */
    public void testSidebarRunningState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.RUNNING_STATE));
        assertFalse(sidebarView.stepNumber.isEnabled());
        assertFalse(sidebarView.addFunctionButton.isEnabled());
        assertFalse(sidebarView.strategySelection.getCallByValue().isEnabled());
        assertFalse((sidebarView.strategySelection.getCallByName().isEnabled()));
        assertFalse(sidebarView.strategySelection.getManualSelection().isEnabled());
        assertFalse(sidebarView.strategySelection.getNormalOrder().isEnabled());
        // TODO assertFalse(sidebarView.nightModeSwitch.isEnabled());
        // TODO Check if share and language button (Sidebar) are enabled in RunningState
    }

    /**
     * Tests if {@link SidebarView}'s widgets are correctly enabled or disabled in PausedState.
     */
    public void testSidebarPausedState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.PAUSED_STATE));
        assertTrue(sidebarView.stepNumber.isEnabled());
        assertFalse(sidebarView.addFunctionButton.isEnabled());
        assertTrue(sidebarView.strategySelection.getCallByValue().isEnabled());
        assertTrue((sidebarView.strategySelection.getCallByName().isEnabled()));
        assertTrue(sidebarView.strategySelection.getManualSelection().isEnabled());
        assertTrue(sidebarView.strategySelection.getNormalOrder().isEnabled());
        // TODO assertFalse(sidebarView.nightModeSwitch.isEnabled());
        // TODO Check if share and language button (Sidebar) are enabled in PausedState
    }

    /**
     * Tests if {@link SidebarView}'s widgets are correctly enabled or disabled in FinishedState.
     */
    public void testSidebarFinishedState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.FINISHED_STATE));
        assertTrue(sidebarView.stepNumber.isEnabled());
        assertFalse(sidebarView.addFunctionButton.isEnabled());
        assertTrue(sidebarView.strategySelection.getCallByValue().isEnabled());
        assertTrue((sidebarView.strategySelection.getCallByName().isEnabled()));
        assertTrue(sidebarView.strategySelection.getManualSelection().isEnabled());
        assertTrue(sidebarView.strategySelection.getNormalOrder().isEnabled());
        // TODO assertFalse(sidebarView.nightModeSwitch.isEnabled());
        // TODO Check if share and language button (Sidebar) are enabled in FinishedState
    }

    /**
     * Tests if {@link SidebarView}'s widgets are correctly enabled or disabled in FinishedFinishedState.
     */
    public void testSidebarFinishedFinishedState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.FINISHED_FINISHED_STATE));
        assertTrue(sidebarView.stepNumber.isEnabled());
        assertTrue(sidebarView.addFunctionButton.isEnabled());
        assertTrue(sidebarView.strategySelection.getCallByValue().isEnabled());
        assertTrue((sidebarView.strategySelection.getCallByName().isEnabled()));
        assertTrue(sidebarView.strategySelection.getManualSelection().isEnabled());
        assertTrue(sidebarView.strategySelection.getNormalOrder().isEnabled());
        // TODO assertFalse(sidebarView.nightModeSwitch.isEnabled());
        // TODO Check if share and language button (Sidebar) are enabled in FinishedFinishedState
    }

    /**
     * Tests if {@link SidebarView}'s widgets are correctly enabled or disabled in StepBeforeResultState.
     */
    public void testSidebarStepBeforeResultState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.STEP_BEFORE_RESULT_STATE));
        assertTrue(sidebarView.stepNumber.isEnabled());
        assertFalse(sidebarView.addFunctionButton.isEnabled());
        assertTrue(sidebarView.strategySelection.getCallByValue().isEnabled());
        assertTrue((sidebarView.strategySelection.getCallByName().isEnabled()));
        assertTrue(sidebarView.strategySelection.getManualSelection().isEnabled());
        assertTrue(sidebarView.strategySelection.getNormalOrder().isEnabled());
        // TODO assertFalse(sidebarView.nightModeSwitch.isEnabled());
        // TODO Check if share and language button (Sidebar) are enabled in StepBeforeResultState
    }

    /**
     * Tests if a user library function is correctly added to the user library and if an AddFunctionEvent is fired.
     */
    public void testAddLibraryFunction() {
        sidebarView.addLibraryItemDialogBox.getNameField().setText("Infinite Loop");
        sidebarView.addLibraryItemDialogBox.getFunctionField().setText("(\\x.x x) (\\x.x x)");
        sidebarView.addLibraryItemDialogBox.getDescriptionField().setText("primitive infinite loop");

        sidebarView.addLibraryItemDialogBox.getAddButton().click();
        Scheduler.get().scheduleDeferred((Command) () -> {
            assertTrue("userLibraryTable should have one entry", sidebarView.userLibraryTable.getRowCount() == 1);
            assertTrue("userLibraryTabel should have 2 column entries",
                    sidebarView.userLibraryTable.getCellCount(0) == 2);
            assertTrue("AddFunctionEvent should be fired once", eventBus.getFiredCount(AddFunctionEvent.TYPE) == 1);
        });
    }

    /**
     * Tests if a user library function is correctly removed from the user library.
     * A DeleteFunctionEvent should be also fired.
     */
    public void testRemoveLibraryFunction() {
        // Add a function to the user library
        sidebarView.addLibraryItemDialogBox.getNameField().setText("Infinite Loop");
        sidebarView.addLibraryItemDialogBox.getFunctionField().setText("(\\x.x x) (\\x.x x)");
        sidebarView.addLibraryItemDialogBox.getDescriptionField().setText("primitive infinite loop");
        sidebarView.addLibraryItemDialogBox.getAddButton().click();

        // view.getSubmitButton.getElement().<ButtonElement>cast().click();
        Scheduler.get().scheduleDeferred((Command) () ->
                sidebarView.userLibraryTable.getWidget(0, 2).getElement().<ButtonElement>cast().click());

        Scheduler.get().scheduleDeferred((Command) () -> {

            assertTrue("userLibraryTable should have no entry",
                    sidebarView.userLibraryTable.getRowCount() == 0);
            assertTrue("DeleteFunctionEvent should be fired once",
                    eventBus.getFiredCount(DeleteFunctionEvent.TYPE) == 1);

        });


    }
}
