package aurora.client.view.popup;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.RootPanel;
import aurora.utils.GWTTestCaseSetup;
import aurora.utils.TestingUtilities;

public class ShareDialogBoxGwtTest extends GWTTestCase {
    private ShareDialogBox shareDialogBox;

    public String getModuleName() {
        return "aurora.Testing";
    }

    /**
     * Sets up the testing environment.
     */
    public void gwtSetUp() {
        GWTTestCaseSetup.cleanUpDOM(RootPanel.get());
        this.shareDialogBox = new ShareDialogBox("I am a caption");
    }

    public void testCaptionIsSet() {
        System.out.println(this.shareDialogBox.getCaption().getText());
        assertEquals("I am a caption", this.shareDialogBox.getCaption().getText());
    }

    /**
     * Tests the copy to clipboard feature.
     */
    public void testCopyToClipboard() {
        this.shareDialogBox.setShareText("I am text to be copied to clipboard.");
        assertEquals("I am text to be copied to clipboard.", this.shareDialogBox.shareText.getText());
        // sidebarView.userLibraryTable.getWidget(0, 2).getElement().<ButtonElement>cast().click());

        this.shareDialogBox.copyToClipboardButton.click();

        Scheduler.get().scheduleDeferred((Command) () ->
                assertEquals(
                        "I am text to be copied to clipboard.", TestingUtilities.getDataFromClipboard()));

    }

    /**
     * Tests the close button.
     */
    public void testCloseButton() {
        this.shareDialogBox.setShareText("Test 1.");
        assertEquals("Test 1.", this.shareDialogBox.shareText.getText());
        this.shareDialogBox.closeButton.click();
        Scheduler.get().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                assertEquals("", ShareDialogBoxGwtTest.this.shareDialogBox.shareText.getText());
            }
        });
    }


}
