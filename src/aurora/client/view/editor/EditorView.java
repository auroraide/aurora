package aurora.client.view.editor;

import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
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

import java.util.List;

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
    DockLayoutPanel outputDockLayoutPanel;
    private Button outputOptionButton;
    private CodeMirrorPanel outputCodeMirror;

    private EventBus eventBus;

    /**
     * Creates the EditorView contents and adds them to their respective parts of the window.
     *
     * @param eventBus Current {@link EventBus} instance.
     */
    public EditorView(EventBus eventBus) {
        this.eventBus = eventBus;
        initWidget(ourUiBinder.createAndBindUi(this));
        setupInputField();
        setupOutputField();
    }

    private void setupInputField() {
        this.inputOptionButton = new Button("Share");
        // TODO Set styling for optionButton
        this.inputDockLayoutPanel.addWest(this.inputOptionButton, 4);

        this.inputCodeMirror = new CodeMirrorPanel();
        this.inputDockLayoutPanel.add(this.inputCodeMirror);
        this.inputDockLayoutPanel.setSize("100%", "100%");

        Scheduler.get().scheduleDeferred(new Command() {
            public void execute() {
                String initialContent = "#Aurorascript static syntax highlighting example";
                initialContent += "\n$plus 2 λs.λz.s(sz)";
                inputCodeMirror.setValue(initialContent);
                //autofocus not working???
                inputCodeMirror.setOption("autofocus", true);
                inputCodeMirror.setOption("mode", "aurorascript");
                inputCodeMirror.setOption("autoCloseBrackets", true);
                inputCodeMirror.setOption("matchBrackets", true);
                inputCodeMirror.setOption("styleActiveLine", true);
            }
        });
    }

    private void setupOutputField() {
        this.outputOptionButton = new Button("Share");
        // TODO Set styling for optionButton
        this.outputDockLayoutPanel.addWest(this.outputOptionButton, 4);

        this.outputCodeMirror = new CodeMirrorPanel();
        this.outputDockLayoutPanel.add(this.outputCodeMirror);
        this.outputDockLayoutPanel.setSize("100%", "100%");

        Scheduler.get().scheduleDeferred(new Command() {
            public void execute() {
                String initialContent = "4";
                initialContent += "\n#Duh";
                outputCodeMirror.setValue(initialContent);
                outputCodeMirror.setOption("readOnly", true);
                outputCodeMirror.setOption("mode", "aurorascript");
                outputCodeMirror.setOption("matchBrackets", true);
            }
        });
    }


    private native void console(String text) /*-{
        console.log(text);
    }-*/;

    @Override
    public void displaySyntaxError(SyntaxException syntaxException) {

    }

    @Override
    public void displaySemanticError(SemanticException semanticException) {

    }

    @Override
    public String getInput() {
        return this.inputCodeMirror.getValue();
    }

    @Override
    public void addNextStep(List<HighlightedLambdaExpression> highlightedLambdaExpressions) {

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
    //TODO Kann das weg???
    // public FlexTable getOutputFieldTable() {
    //     return outputFieldTable;
    // }

    /**
     * Returns the ActionBar.
     *
     * @return actionBar
     */
    public ActionBar getActionBar() {
        return actionBar;
    }

}

