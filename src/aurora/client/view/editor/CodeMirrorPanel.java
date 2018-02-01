package aurora.client.view.editor;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.core.client.JavaScriptObject;

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
        return callCMFunction(editor, option);
    }

    /**
     * Set the value for a given option.
     *
     * @param option Option which to set.
     * @param value  New value of the option.
     */
    public void setOption(String option, Object value) {
        callCMFunction(editor, option, value);
    }

    private native Object callCMFunction(JavaScriptObject jso, String name) /*-{
        return jso[name].apply(jso);
    }-*/;

    private native Object callCMFunction(JavaScriptObject jso, String name, Object arg) /*-{
        return jso[name].apply(jso, [arg]);
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

}
