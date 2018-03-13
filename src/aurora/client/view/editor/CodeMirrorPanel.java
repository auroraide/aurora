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
     * Allows setting of events.
     * Referencing the the CodeMirrorPanel from within the function is done using "editor".
     *
     * @param event which event to set
     * @param func what to execute
     */
    public void on(String event, String func) {
        applyCMFunction(editor, "on", event, "(" + func + ")");
    }

    private native Object callCMFunction(JavaScriptObject jso, String key) /*-{
        return jso[key].apply(jso);
    }-*/;

    private native Object callCMFunction(JavaScriptObject jso, String key, Object arg) /*-{
        return jso[key].apply(jso, [arg]);
    }-*/;

    private native Object callCMFunction(JavaScriptObject jso, String key, Object arg1, Object arg2) /*-{
        return jso[key].apply(jso, [arg1, arg2]);
    }-*/;

    //Do NOT rename the JavaScriptObject editor!
    //It WILL break stuff.
    private native Object applyCMFunction(JavaScriptObject editor, String key, String event, Object func) /*-{
        return editor[key].apply(editor, [event, eval(func)]);
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
