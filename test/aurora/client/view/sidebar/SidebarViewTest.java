package aurora.client.view.sidebar;

import aurora.client.event.AddFunctionEvent;
import aurora.client.event.DeleteFunctionEvent;
import aurora.client.event.ViewStateChangedEvent;
import aurora.client.view.ViewState;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.event.shared.testing.CountingEventBus;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.RootPanel;
import aurora.utils.GWTTestCaseSetup;


/**
 * Testing {@link SidebarView}.
 */
public class SidebarViewTest extends GWTTestCase {
    private SidebarView sidebarView;
    private CountingEventBus eventBus;

    public String getModuleName() {
        return "aurora.Testing";
    }


    /**
     * Sets up the testing environment.
     */
    public void gwtSetUp() {
        GWTTestCaseSetup.cleanUpDOM(RootPanel.get());

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

        // check, if nighMode is enabled on initialisation
        assertTrue(sidebarView.nightModeSwitch.getValue());
    }

    /**
     * Tests if {@link SidebarView}'s widgets are correctly enabled or disabled in DefaultState.
     */
    public void testSidebarDefaultState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.DEFAULT_STATE));
        assertSidebarWidgetsEnabledDisabled(true, true, true, true, true,true, true);
        //TODO Check if share and language button (Sidebar) are enabled in DefaultState
    }

    /**
     * Tests if {@link SidebarView}'s widgets are correctly enabled or disabled in RunningState.
     */
    public void testSidebarRunningState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.RUNNING_STATE));
        assertSidebarWidgetsEnabledDisabled(false, false, false,false, false,false, false);
        // TODO Check if share and language button (Sidebar) are enabled in RunningState
    }

    /**
     * Tests if {@link SidebarView}'s widgets are correctly enabled or disabled in PausedState.
     */
    public void testSidebarPausedState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.PAUSED_STATE));
        assertSidebarWidgetsEnabledDisabled(true, true, true, true, true,true, true);
        // TODO Check if share and language button (Sidebar) are enabled in PausedState
    }

    /**
     * Tests if {@link SidebarView}'s widgets are correctly enabled or disabled in FinishedState.
     */
    public void testSidebarFinishedState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.FINISHED_STATE));
        assertSidebarWidgetsEnabledDisabled(true, true, true, true, true,true, true);
        // TODO Check if share and language button (Sidebar) are enabled in FinishedState
    }

    /**
     * Tests if {@link SidebarView}'s widgets are correctly enabled or disabled in FinishedFinishedState.
     */
    public void testSidebarFinishedFinishedState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.FINISHED_FINISHED_STATE));
        assertSidebarWidgetsEnabledDisabled(true, true, true, true, true,true, true);
        // TODO Check if share and language button (Sidebar) are enabled in FinishedFinishedState
    }

    /**
     * Tests if {@link SidebarView}'s widgets are correctly enabled or disabled in StepBeforeResultState.
     */
    public void testSidebarStepBeforeResultState() {
        eventBus.fireEvent(new ViewStateChangedEvent(ViewState.STEP_BEFORE_RESULT_STATE));
        assertSidebarWidgetsEnabledDisabled(true, true, true, true, true,true, true);
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

    /**
     * Regression test. Add library function and remove it. Repeating the procedure twice should not fail.
     */
    public void testAddRemoveAddLibraryFunction() {
        testRemoveLibraryFunction();
        testRemoveLibraryFunction();
    }

    /**
     * Checks if {@link SidebarView} widgets are enabled or disabled.
     *
     * The parameters describe the expectation, whether a the widgets are enabled or disabled.
     *
     * @param stepNumberIsEnabled The expected value of stepNumber widget.
     * @param addFunctionButtonIsEnabled The expected value of addFunctionButton.
     * @param callByValueIsEnabled The expected value of callByValue widget.
     * @param callByNameIsEnabled The expected value of callByName widget.
     * @param manualSelectionIsEnabled The expected value of manualSelection widget.
     * @param normalOrderIsEnabled The expected value of normalOrder widget.
     * @param nightModeSwitchIsEnabled The expected value of nightModeSwitch.
     */
    private void assertSidebarWidgetsEnabledDisabled(boolean stepNumberIsEnabled, boolean addFunctionButtonIsEnabled,
                                                   boolean callByValueIsEnabled, boolean callByNameIsEnabled,
                                                   boolean manualSelectionIsEnabled, boolean normalOrderIsEnabled,
                                                   boolean nightModeSwitchIsEnabled) {
        assertEquals(stepNumberIsEnabled, sidebarView.stepNumber.isEnabled());
        assertEquals(addFunctionButtonIsEnabled, sidebarView.addFunctionButton.isEnabled());
        assertEquals(callByValueIsEnabled, sidebarView.strategySelection.getCallByValue().isEnabled());
        assertEquals(callByNameIsEnabled, sidebarView.strategySelection.getCallByName().isEnabled());
        assertEquals(manualSelectionIsEnabled, sidebarView.strategySelection.getManualSelection().isEnabled());
        assertEquals(normalOrderIsEnabled, sidebarView.strategySelection.getNormalOrder().isEnabled());
        assertEquals(nightModeSwitchIsEnabled, sidebarView.nightModeSwitch.isEnabled());

    }
}
