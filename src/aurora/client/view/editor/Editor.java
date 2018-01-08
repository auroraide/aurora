package aurora.client.view.editor;

import aurora.client.view.editor.components.ActionBar;
import aurora.client.view.editor.components.OutputField;
import aurora.client.view.editor.components.StepField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import org.geomajas.codemirror.client.widget.CodeMirrorPanel;


public class Editor extends Composite {
    interface EditorUiBinder extends UiBinder<Widget, Editor> {}
    private static final EditorUiBinder ourUiBinder = GWT.create(EditorUiBinder.class);

    // Input Field
    @UiField FlexTable inputFieldTable;
    private Button inputOptionButton;
    private CodeMirrorPanel inputCodeMirror;
    @UiField ActionBar actionBar;

    // Step Field
    @UiField FlexTable stepFieldTable;

    // Output Field
    @UiField FlexTable outputFieldTable;
    private Button outputOptionButton;
    private CodeMirrorPanel outputCodeMirror;



    public Editor() {
        initWidget(ourUiBinder.createAndBindUi(this));
        setupInputCodeMirror();
        setupOutputFieldCodeMirror();
    }

    private void setupInputCodeMirror() {
        this.inputOptionButton = new Button();
        // TODO Set styling for optionButton
        this.inputCodeMirror = new CodeMirrorPanel();
        // TODO Add inputCodeMirror and option button to inputFieldContainer
    }

    private void setupOutputFieldCodeMirror() {
        this.outputOptionButton = new Button();
        // TODO Set styling for optionButton
        this.outputCodeMirror = new CodeMirrorPanel();
        // TODO Add outputCodeMirror and option button to outputFieldContainer
    }

    public CodeMirrorPanel getInputCodeMirror() {
        return inputCodeMirror;
    }

    public CodeMirrorPanel getOutputCodeMirror() {
        return outputCodeMirror;
    }

    public FlexTable getOutputFieldTable() {
        return outputFieldTable;
    }

    public ActionBar getActionBar() {
        return actionBar;
    }

}