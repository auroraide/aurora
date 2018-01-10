package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Called when user wants to copy text to the clipboard.
 */
public interface CopyToClipboardEventHandler extends EventHandler {

    /**
     * Called when user wants to copy text to the clipboard.
     *
     * @param event User input.
     */
    void onCopyToClipboard(CopyToClipboardEvent event);
}
