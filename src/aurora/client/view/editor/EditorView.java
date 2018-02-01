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
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import aurora.client.view.editor.CodeMirrorPanel;
import com.google.gwt.user.client.Timer;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;

/**
 * This is where the user may view and manipulate code.
 * Three different kinds of code fields are provided.
 * An input field, which can be viewed and edited, as well as steps and an output field which can only be viewed.
 */
public class EditorView extends Composite implements EditorDisplay {
    interface EditorUiBinder extends UiBinder<Widget, EditorView> {
    }

    private static final EditorUiBinder ourUiBinder = GWT.create(EditorUiBinder.class);

    // Input Field
    @UiField
    DockLayoutPanel inputDockLayoutPanel;
    private Button inputOptionButton;
    private CodeMirrorPanel inputCodeMirror;
    @UiField
    ActionBar actionBar;

    // Step Field
    @UiField
    FlexTable stepFieldTable;

    // Output Field
    @UiField
    FlexTable outputFieldTable;

    private EventBus eventBus;
    private Button outputOptionButton;
    private CodeMirrorPanel outputCodeMirror;


    /**
     * Creates the EditorView contents and adds them to their respective parts of the window.
     *
     * @param eventBus Current {@link EventBus} instance.
     */
    public EditorView(EventBus eventBus) {
        this.eventBus = eventBus;
        initWidget(ourUiBinder.createAndBindUi(this));
        setupInputCodeMirror();
        setupOutputFieldCodeMirror();
    }

    private void setupInputCodeMirror() {
        this.inputOptionButton = new Button("Share");
        // TODO Set styling for optionButton

        this.inputCodeMirror = new CodeMirrorPanel();
        this.inputDockLayoutPanel.addWest(this.inputOptionButton, 4);
        this.inputDockLayoutPanel.add(this.inputCodeMirror);
        this.inputDockLayoutPanel.setSize("100%", "100%");

        Scheduler.get().scheduleDeferred(new Command() {
            public void execute() {
                String initialContent = "#Aurorascript static syntax highlighting example";
                initialContent += "\n$plus 2 λs.λz.s(sz)";
                inputCodeMirror.setValue(initialContent);
                inputCodeMirror.setOption("mode", "aurorascript");
            }
        });
    }

    private void setupOutputFieldCodeMirror() {
        this.outputOptionButton = new Button();
        // TODO Set styling for optionButton
        this.outputCodeMirror = new CodeMirrorPanel();
        // TODO Add outputCodeMirror and option button to outputFieldContainer
    }


    private native void console(String text) /*-{
        console.log(text);
    }-*/;

    @Override
    public void displaySyntaxError(String message) {

    }

    @Override
    public String getInput() {
        return null;
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

    @Override
    public void setInput(HighlightedLambdaExpression highlightedLambdaExpression) {

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

}

