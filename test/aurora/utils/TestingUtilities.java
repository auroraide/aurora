package aurora.utils;

/**
 * Provides often used methods in testing.
 */
public class TestingUtilities {

    /**
     * Gets the current text from clipboard.
     *
     * @return The text from clipboard.
     */
    public static native String getDataFromClipboard() /*-{
        return $wnd.clipboardData.getData('Text');
    }-*/;
}
