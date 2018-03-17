package aurora.client.view.popup;

import aurora.utils.GWTTestCaseSetup;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.RootPanel;

public class AddLibraryItemDialogBoxGwtTest extends GWTTestCase {
    private final String infiniteLoopName = "infinite loop";
    private final String infiniteLoopFunc = "(\\x.x x) (\\x.x x)";
    private final String infiniteLoopDesc = "simple infinite loop";

    private AddLibraryItemDialogBox addLibItemDB;


    public String getModuleName() {
        return "aurora.Aurora";
    }

    /**
     * Sets up the testing environment.
     */
    public void gwtSetUp() {
        GWTTestCaseSetup.cleanUpDOM(RootPanel.get());
        addLibItemDB = new AddLibraryItemDialogBox();
    }

    public void testIsDisabledOnInitialization() {
        assertFalse(this.addLibItemDB.isAttached());
        RootPanel.get().add(addLibItemDB);
    }

    /**
     * Tests cancelButton behaviour when clicked.
     *
     * After clicking the cancel button,
     * the {@link AddLibraryItemDialogBox}'s TextBoxes should be cleared.
     */
    public void testCancelButtonClicked() {
        this.addLibItemDB.show();
        assertTrue(this.addLibItemDB.isAttached());

        Scheduler.get().scheduleDeferred((Command) () -> {
            addLibItemDB.nameField.setText(infiniteLoopName);
            addLibItemDB.functionField.setText(infiniteLoopFunc);
            addLibItemDB.descriptionField.setText(infiniteLoopDesc);
            assertEquals(infiniteLoopName, addLibItemDB.nameField.getText());
            assertEquals(infiniteLoopFunc, addLibItemDB.functionField.getText());
            assertEquals(infiniteLoopDesc, addLibItemDB.descriptionField.getText());

            this.addLibItemDB.cancelButton.click();
        });

        Scheduler.get().scheduleDeferred((Command) () -> {
            assertFalse("addLibItemDB should not be attached to DOM, when clicked on cancel",
                     addLibItemDB.isAttached());

            assertEquals("", addLibItemDB.nameField.getText());
            assertEquals("", addLibItemDB.functionField.getText());
            assertEquals("", addLibItemDB.descriptionField.getText());
        });
    }

    public void testBackslash2Lambda() {
    }


}
