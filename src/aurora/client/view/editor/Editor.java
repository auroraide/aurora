package aurora.client.view.editor;

import aurora.client.view.editor.actionbar.ActionBar;
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


    /**
     * Creates the Editor contents and adds them to their respective parts of the window;
     */
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

    /**
     * Returns the inputCodeMirror
     *
     * @return inputCodeMirror
     */
    public CodeMirrorPanel getInputCodeMirror() {
        return inputCodeMirror;
    }

    /**
     * Returns the inputOptionButton
     *
     * @return inputOptionButton
     */
    public Button getInputOptionButton() {
        return inputOptionButton;
    }

    /**
     * Returns the outputCodeMirror
     *
     * @return outputCodeMirror
     */
    public CodeMirrorPanel getOutputCodeMirror() {
        return outputCodeMirror;
    }

    /**
     * Returns the outputOptionButton
     *
     * @return outputOptionButton
     */
    public Button getOutputOptionButton() {
        return outputOptionButton;
    }

    /**
     * Returns the outputFieldTable
     *
     * @return outputFieldTable
     */
    public FlexTable getOutputFieldTable() {
        return outputFieldTable;
    }

    /**
     * Returns the ActionBar
     *
     * @return actionBar
     */
    public ActionBar getActionBar() {
        return actionBar;
    }

}
