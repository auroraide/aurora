package aurora.client.view.editor;

import aurora.backend.HighlightedLambdaExpression;
import aurora.client.EditorDisplay;
import aurora.client.view.editor.actionbar.ActionBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import org.geomajas.codemirror.client.widget.CodeMirrorPanel;

/**
 * This is where the user may view and manipulate code.
 * Three different kinds of code fields are provided.
 * An input field, which can be viewed and edited, as well as steps and an output field which can only be viewed.
 */
public class EditorView extends Composite implements EditorDisplay {
    private static final EditorUiBinder ourUiBinder = GWT.create(EditorUiBinder.class);
    // Input Field
    @UiField
    FlexTable inputFieldTable;
    @UiField
    ActionBar actionBar;
    // Step Field
    @UiField
    FlexTable stepFieldTable;
    // Output Field
    @UiField
    FlexTable outputFieldTable;
    private EventBus eventBus;
    private Button inputOptionButton;
    private CodeMirrorPanel inputCodeMirror;
    private Button outputOptionButton;
    private CodeMirrorPanel outputCodeMirror;

    /**
     * Creates the EditorView contents and adds them to their respective parts of the window.
     *
     * @param eventBus Current instance of the {@link EventBus}.
     */
    public EditorView(EventBus eventBus) {
        this.eventBus = eventBus;
        initWidget(ourUiBinder.createAndBindUi(this));
        setupInputCodeMirror();
        setupOutputFieldCodeMirror();
    }

    @Override
    public void displaySyntaxError(String message) {

    }

    @Override
    public String getInput() {
        return null;
    }

    @Override
    public void setInput(HighlightedLambdaExpression highlightedLambdaExpression) {

    }

    @Override
    public void addNextStep(HighlightedLambdaExpression highlightedLambdaExpression) {

    }

    @Override
    public void resetSteps() {

    }

    @Override
    public void displayResult(HighlightedLambdaExpression highlightedLambdaExpression) {

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
     * Returns the inputCodeMirror.
     *
     * @return inputCodeMirror
     */
    public CodeMirrorPanel getInputCodeMirror() {
        return inputCodeMirror;
    }

    /**
     * Returns the inputOptionButton.
     *
     * @return inputOptionButton
     */
    public Button getInputOptionButton() {
        return inputOptionButton;
    }

    /**
     * Returns the outputCodeMirror.
     *
     * @return outputCodeMirror
     */
    public CodeMirrorPanel getOutputCodeMirror() {
        return outputCodeMirror;
    }

    /**
     * Returns the outputOptionButton.
     *
     * @return outputOptionButton
     */
    public Button getOutputOptionButton() {
        return outputOptionButton;
    }

    /**
     * Returns the outputFieldTable.
     *
     * @return outputFieldTable
     */
    public FlexTable getOutputFieldTable() {
        return outputFieldTable;
    }

    /**
     * Returns the ActionBar.
     *
     * @return actionBar
     */
    public ActionBar getActionBar() {
        return actionBar;
    }

    interface EditorUiBinder extends UiBinder<Widget, EditorView> {
    }

}

