package aurora.client.view.editor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;

/**
 * Panel containing CodeMirror.
 * This panel provides a wrapper for CodeMirror.
 * It also allows its configuration by forwarding commands.
 */
public class CodeMirrorPanel extends SimplePanel {

    private TextArea textArea;
    private JavaScriptObject editor;

    /**
     * Creates a new CodeMirrorPanel using default settings.
     */
    public CodeMirrorPanel() {
        super();
        setSize("100%", "100%");
        textArea = new TextArea();
        setWidget(textArea);
        attachCM();
    }

    /**
     * Returns the content of the panel.
     *
     * @return content Content of the panel.
     */
    public String getValue() {
        return (String) callCMFunction(editor, "getValue");
    }

    /**
     * Sets the content of the panel.
     *
     * @param content New content of the panel.
     */
    public void setValue(String content) {
        callCMFunction(editor, "setValue", content);
    }

    /**
     * Returns the status of a given option.
     *
     * @return value Status of the option asked.
     */
    public Object getOption(String option) {
        return callCMFunction(editor, "getOption", option);
    }

    /**
     * Set the value for a given option.
     *
     * @param option Option which to set.
     * @param value  New value of the option.
     */
    public void setOption(String option, Object value) {
        callCMFunction(editor, "setOption", option, value);
    }

    /**
     * Get the amount of lines in this panel.
     *
     * @return amount of lines in panel.
     */
    public int lineCount() {
        return callCMFunctionInt(editor, "lineCount");
    }

    /**
     * Get the content of a given line.
     *
     * @param lineNumber content of which line to get.
     * @return gets content of given line.
     */
    public String getLine(int lineNumber) {
        return (String) callCMFunction(editor, "getLine", lineNumber);
    }

    /**
     * Give focus to the CMP.
     */
    public void focus() {
        callCMFunction(editor, "focus");
    }

    /**
     * Mark text in a given interval.
     * Requires selection/mark-selection.js addon to be loaded.
     *
     * @param fromLine line in which to begin marking.
     * @param fromChar first char to mark in fromLine..
     * @param toLine line in which to stop marking.
     * @param toChar last char to mark in toLine.
     */
    public Object markText(int fromLine, int fromChar, int toLine, int toChar, String classCSS) {
        callCMmarkText(editor, "markText", fromLine, fromChar, toLine, toChar, classCSS);
    }

    /**
     * Removes a marker from the editor.
     */
    public void deleteMarker(Object marker) {
        callCMFunction((JavaScriptObject) marker, "clear");
    }

    private native Object callCMmarkText(JavaScriptObject jso, String key,
            Object arg1, Object arg2, Object arg3, Object arg4, String arg5) /*-{
        return jso[key].apply(jso,
            [{line: arg1,ch: arg2},
            {line: arg3, ch: arg4},
            {className: "highlight-" + arg5}]);
    }-*/;

    private native Object callCMFunction(JavaScriptObject jso, String key) /*-{
        return jso[key].apply(jso);
    }-*/;

    private native Object callCMFunction(JavaScriptObject jso, String key, Object arg) /*-{
        return jso[key].apply(jso, [arg]);
    }-*/;

    private native Object callCMFunction(JavaScriptObject jso, String key, Object arg1, Object arg2) /*-{
        return jso[key].apply(jso, [arg1, arg2]);
    }-*/;

    private native int callCMFunctionInt(JavaScriptObject jso, String key) /*-{
        return jso[key].apply(jso);
    }-*/;

    private static native void console(String text) /*-{
        console.log(text);
    }-*/;

    private void attachCM() {
        Scheduler.get().scheduleDeferred(new Command() {
            public void execute() {
                editor = createCM(textArea.getElement().<TextAreaElement>cast());
            }
        });
    }

    private static native JavaScriptObject createCM(TextAreaElement tae) /*-{
        return $wnd.CodeMirror.fromTextArea(tae, {lineNumbers: true});
    }-*/;

    private String getEditor() {
        return String.valueOf(this.editor);
    }
}
