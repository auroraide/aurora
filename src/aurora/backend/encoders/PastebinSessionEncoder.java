package aurora.backend.encoders;

import com.github.kennedyoliveira.pastebin4j.Paste;
import com.github.kennedyoliveira.pastebin4j.PasteBin;
import com.github.kennedyoliveira.pastebin4j.AccountCredentials;
import aurora.backend.encoders.exceptions.DecodeException;

/**
 * Save/restore sessions on <a href="https://pastebin.com">pastebin.com</a>.
 */
public class PastebinSessionEncoder extends SessionEncoder {

    private final String devKey = "e09a05a00409086d72e3dfae85d341c8";

    @Override
    public String encode(Session session) {
        final PasteBin pasteBin = new PasteBin(getCredentials());
        final Paste paste = new Paste();
        paste.setTitle("Created with AURORA");
        paste.setContent("Banana");
        final String url = pasteBin.createPaste(paste);
        console(url);
        return url;
    }

    @Override
    public Session decode(String encodedInput) throws DecodeException {
        return null;
    }

    private AccountCredentials getCredentials() {
        return new AccountCredentials(devKey);
    }

    private static native void console(String text) /*-{
        console.log(text);
    }-*/;

}
