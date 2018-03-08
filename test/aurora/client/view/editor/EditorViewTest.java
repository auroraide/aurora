package aurora.client.view.editor;

import aurora.client.event.ViewStateChangedEvent;
import aurora.client.view.ViewState;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.testing.CountingEventBus;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests {@link EditorView}.
 */
public class EditorViewTest extends GWTTestCase {
    private CountingEventBus eventBus;
    private EditorView editorView;

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
        editorView = new EditorView(eventBus);
    }

    /**
     * Test {@link EditorView}'s widgets values on initialisation.
     */
    public void testInitialisation() {
        Scheduler scheduler = Scheduler.get();
        scheduler.scheduleDeferred((Command) () -> {
            // input, steps and output fields
            assertTrue(editorView.getInput().equals(""));
            assertTrue(editorView.getInputCodeMirror().equals(""));
            assertTrue(editorView.stepFieldTable.getRowCount() == 0);
            assertTrue(editorView.getOutputCodeMirror().getValue() == "");

            // Check ActionBar widgets, whether enabled/disabled or hidden
            assertTrue(editorView.getActionBar().getRunButton().isEnabled()
                    && editorView.getActionBar().getRunButton().isVisible());
            assertFalse(editorView.getActionBar().getContinueButton().isEnabled()
                    && editorView.getActionBar().getPauseButton().isVisible());
            assertFalse(editorView.getActionBar().getPauseButton().isEnabled()
                    && editorView.getActionBar().getContinueButton().isVisible());
            assertFalse(editorView.getActionBar().getResetButton().isEnabled()
                    && !editorView.getActionBar().getStepButton().isVisible());
            assertTrue(editorView.getActionBar().getStepButton().isEnabled()
                    && editorView.getActionBar().getResetButton().isVisible());

        });
    }

    /**
     * Test widgets, if properly enabled or disabled in DefaultState.
     */
    public void testDefaultState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.DEFAULT_STATE));
        assertTrue(editorView.getActionBar().getRunButton().isEnabled()
                && editorView.getActionBar().getRunButton().isVisible());
        assertFalse(editorView.getActionBar().getPauseButton().isEnabled()
                && editorView.getActionBar().getPauseButton().isVisible());
        assertFalse(editorView.getActionBar().getContinueButton().isEnabled()
                && editorView.getActionBar().getContinueButton().isVisible());
        assertFalse(editorView.getActionBar().getStepButton().isEnabled()
                && !editorView.getActionBar().getStepButton().isVisible());
        assertFalse(editorView.getActionBar().getResetButton().isEnabled()
                && !editorView.getActionBar().getResetButton().isVisible());
    }

    /**
     * Test widgets, if properly enabled or disabled in RunningState.
     */
    public void testRunningState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.RUNNING_STATE));
        assertFalse(editorView.getActionBar().getRunButton().isEnabled()
                && editorView.getActionBar().getRunButton().isVisible());
        assertTrue(editorView.getActionBar().getPauseButton().isEnabled()
                && editorView.getActionBar().getPauseButton().isVisible());
        assertFalse(editorView.getActionBar().getContinueButton().isEnabled()
                && editorView.getActionBar().getContinueButton().isVisible());
        assertFalse(editorView.getActionBar().getStepButton().isEnabled()
                && !editorView.getActionBar().getStepButton().isVisible());
        assertFalse(editorView.getActionBar().getResetButton().isEnabled()
                && !editorView.getActionBar().getResetButton().isVisible());
    }

    /**
     * Test widgets, if properly enabled or disabled in PausedState.
     */
    public void testPausedState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.PAUSED_STATE));
        assertFalse(editorView.getActionBar().getRunButton().isEnabled()
                && editorView.getActionBar().getRunButton().isVisible());
        assertFalse(editorView.getActionBar().getPauseButton().isEnabled()
                && editorView.getActionBar().getPauseButton().isVisible());
        assertTrue(editorView.getActionBar().getContinueButton().isEnabled()
                && editorView.getActionBar().getContinueButton().isVisible());
        assertTrue(editorView.getActionBar().getStepButton().isEnabled()
                && editorView.getActionBar().getStepButton().isVisible());
        assertTrue(editorView.getActionBar().getResetButton().isEnabled()
                && editorView.getActionBar().getResetButton().isVisible());
    }

    /**
     * Test widgets, if properly enabled or disabled in FinishedState.
     */
    public void testFinishedState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.FINISHED_STATE));
        assertFalse(editorView.getActionBar().getRunButton().isEnabled()
                && editorView.getActionBar().getRunButton().isVisible());
        assertFalse(editorView.getActionBar().getPauseButton().isEnabled()
                && editorView.getActionBar().getPauseButton().isVisible());
        assertFalse(editorView.getActionBar().getContinueButton().isEnabled()
                && editorView.getActionBar().getContinueButton().isVisible());
        assertTrue(editorView.getActionBar().getStepButton().isEnabled()
                && editorView.getActionBar().getStepButton().isVisible());
        assertTrue(editorView.getActionBar().getResetButton().isEnabled()
                && editorView.getActionBar().getResetButton().isVisible());
    }

    /**
     * Test widgets, if properly enabled or disabled in FinishedFinishedState.
     */
    public void testFinishedFinishedState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.FINISHED_FINISHED_STATE));
        assertFalse(editorView.getActionBar().getRunButton().isEnabled()
                && !editorView.getActionBar().getRunButton().isVisible());
        assertFalse(editorView.getActionBar().getPauseButton().isEnabled()
                && editorView.getActionBar().getPauseButton().isVisible());
        assertFalse(editorView.getActionBar().getContinueButton().isEnabled()
                && editorView.getActionBar().getContinueButton().isVisible());
        assertFalse(editorView.getActionBar().getStepButton().isEnabled()
                && !editorView.getActionBar().getStepButton().isVisible());
        assertTrue(editorView.getActionBar().getResetButton().isEnabled()
                && editorView.getActionBar().getResetButton().isVisible());
    }

    /**
     * Test widgets, if properly enabled or disabled in StepBeforeResultState.
     */
    public void testStepBeforeResultState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.STEP_BEFORE_RESULT_STATE));
        assertTrue(editorView.getActionBar().getRunButton().isEnabled()
                && editorView.getActionBar().getRunButton().isVisible());
        assertFalse(editorView.getActionBar().getPauseButton().isEnabled()
                && editorView.getActionBar().getPauseButton().isVisible());
        assertFalse(editorView.getActionBar().getContinueButton().isEnabled()
                && editorView.getActionBar().getContinueButton().isVisible());
        assertTrue(editorView.getActionBar().getStepButton().isEnabled()
                && editorView.getActionBar().getStepButton().isVisible());
        assertTrue(editorView.getActionBar().getResetButton().isEnabled()
                && editorView.getActionBar().getResetButton().isVisible());
    }

}