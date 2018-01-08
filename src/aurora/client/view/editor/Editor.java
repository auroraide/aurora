package aurora.client.view.editor;

import aurora.client.view.editor.components.ActionBar;
import aurora.client.view.editor.components.OutputField;
import aurora.client.view.editor.components.StepField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import org.geomajas.codemirror.client.widget.CodeMirrorPanel;


public class Editor extends Composite {
    interface EditorUiBinder extends UiBinder<Widget, Editor> {}
    private static final EditorUiBinder ourUiBinder = GWT.create(EditorUiBinder.class);

    CodeMirrorPanel inputField;
    @UiField FlowPanel inputFieldContainer;
    @UiField ActionBar actionBar;
    /*@UiField*/ StepField stepField;
    /*@UiField*/ OutputField outputField;

    public Editor() {
        initWidget(ourUiBinder.createAndBindUi(this));
        setUpCodeMirror();
    }

    private void setUpCodeMirror() {
        this.inputField = new CodeMirrorPanel();
        inputFieldContainer.add(inputField);
    }

    public CodeMirrorPanel getCodeEditor() {
        return inputField;
    }

    public ActionBar getActionBar() {
        return actionBar;
    } /* warum? nicht innerhalb den Actionbar?*/

    public StepField getStepField() {
        return stepField;
    }

    public OutputField getOutputField() {
        return outputField;
    }
}