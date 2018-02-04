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
        attachCm();
    }

    /**
     * Returns the content of the panel.
     *
     * @return content Content of the panel.
     */
    public String getValue() {
        return (String) callCmFunction(editor, "getValue");
    }

    /**
     * Sets the content of the panel.
     *
     * @param content New content of the panel.
     */
    public void setValue(String content) {
        callCmFunction(editor, "setValue", content);
    }

    /**
     * Returns the status of a given option.
     *
     * @return value Status of the option asked.
     */
    public Object getOption(String option) {
        return callCmFunction(editor, option);
    }

    /**
     * Set the value for a given option.
     *
     * @param option Option which to set.
     * @param value  New value of the option.
     */
    public void setOption(String option, Object value) {
        callCmFunction(editor, "setOption", option, value);
    }

    private native Object callCmFunction(JavaScriptObject jso, String name) /*-{
        return jso[name].apply(jso);
    }-*/;

    private native Object callCmFunction(JavaScriptObject jso, String name, Object arg) /*-{
        return jso[name].apply(jso, [arg]);
    }-*/;

    private native Object callCmFunction(JavaScriptObject jso, String name, Object arg1, Object arg2) /*-{
        return jso[name].apply(jso, [arg1, arg2]);
    }-*/;

    private static native void console(String text) /*-{
        console.log(text);
    }-*/;

    private void attachCm() {
        Scheduler.get().scheduleDeferred(new Command() {
            public void execute() {
                editor = createCm(textArea.getElement().<TextAreaElement>cast());
            }
        });
    }

    private static native JavaScriptObject createCm(TextAreaElement tae) /*-{
        return $wnd.CodeMirror.fromTextArea(tae, {lineNumbers: true});
    }-*/;

    private String getEditor() {
        return String.valueOf(this.editor);
    }
}
