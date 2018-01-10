package aurora.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface CopyToClipboardEventHandler extends EventHandler {
    void onCopyToClipboard(CopyToClipboardEvent event);
}
