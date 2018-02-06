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
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

import aurora.client.view.popup.InfoDialogBox;

import java.util.List;
import java.util.LinkedList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

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

    private InfoDialogBox infoDialogBox;

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
        setupInfoDialogBox();
        stepFieldTable.setSize("100%", "100%");
    }

    private void setupInputField() {
        this.inputOptionButton = new Button("Share");
        // TODO Set styling for optionButton
        this.inputDockLayoutPanel.addWest(setupInputMenuBar(), 4);
        this.inputDockLayoutPanel.addWest(setupInputMenuBarDEBUG(), 4);

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

    private MenuBar setupInputMenuBar() {
        MenuBar optionsMenuBar = new MenuBar(true);
        optionsMenuBar.addItem("toggle VIM", new Command() {
            public void execute() {
                if(inputCodeMirror.getOption("keyMap").equals("default")) {
                    inputCodeMirror.setOption("keyMap", "vim");
                }
                else {
                    inputCodeMirror.setOption("keyMap", "default");
                }
            }
        });
        return optionsMenuBar;
    }

    private MenuBar setupInputMenuBarDEBUG() {
        MenuBar debugMenuBar = new MenuBar(true);

        debugMenuBar.addItem("add 5 Steps", new Command() {
            public void execute() {
                List<String> strings = new LinkedList<String>();
                strings.add("#Ugly as a blobfish, but hey, it works :)");
                strings.add("#Should add a scrollbar, shouldn't I\n$plus");
                strings.add("third #<- not a comment :)");
                strings.add("$plus 2 λs.λz.s(sz)");
                strings.add("whatchaknow\nnever thought you'd make it down here");
                addNextStepDEBUG(strings);
            }
        });

        debugMenuBar.addItem("remove Steps", new Command() {
            public void execute() {
                resetSteps();
            }
        });

        debugMenuBar.addItem("show Error Popup", new Command() {
            public void execute() {
                displaySyntaxError("This is an error");
            }
        });

        return debugMenuBar;
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

    private void setupInfoDialogBox() {
        this.infoDialogBox = new InfoDialogBox();    
    }


    @Override
    public void displaySyntaxError(String message) {
        this.infoDialogBox.setTitle(message);
        this.infoDialogBox.setDescription("42");
        this.infoDialogBox.show();
    }

    @Override
    public String getInput() {
        return this.inputCodeMirror.getValue();
    }

    @Override
    public void addNextStep(List<HighlightedLambdaExpression> highlightedLambdaExpressions) {
        highlightedLambdaExpressions.forEach((hle) -> {
            addStepEntry(stepFieldTable.getRowCount(), hle);
        });
    }

    private void addStepEntry(int entryIndex, HighlightedLambdaExpression hle) {
        stepFieldTable.setText(entryIndex, 0, Integer.toString(entryIndex + 1));
        stepFieldTable.setWidget(entryIndex, 1, new Button("OptionsButton, config me"));
        CodeMirrorPanel cmp = new CodeMirrorPanel();

        //TODO: once hle is done, use its magic
        Scheduler.get().scheduleDeferred(new Command() {
            public void execute() {
                cmp.setValue(hle.toString());
                cmp.setOption("readOnly", true);
                cmp.setOption("mode", "aurorascript");
                cmp.setOption("matchBrackets", true);
            }
        });
        stepFieldTable.setWidget(entryIndex, 2, cmp);
    }

    //TODO:remove once hle is done
    private void addNextStepDEBUG(List<String> highlightedLambdaExpressions) {
        highlightedLambdaExpressions.forEach((hle) -> {
            addStepEntryDEBUG(stepFieldTable.getRowCount(), hle);
        });
    }

    //TODO:remove once hle is done
    private void addStepEntryDEBUG(int entryIndex, String notAnHle) {
        stepFieldTable.setText(entryIndex, 0, Integer.toString(entryIndex + 1));
        stepFieldTable.setWidget(entryIndex, 1, new Button("OptionsButton, config me"));
        CodeMirrorPanel cmp = new CodeMirrorPanel();

        Scheduler.get().scheduleDeferred(new Command() {
            public void execute() {
                cmp.setValue(notAnHle);
                cmp.setOption("readOnly", true);
                cmp.setOption("mode", "aurorascript");
                cmp.setOption("matchBrackets", true);
            }
        });
        stepFieldTable.setWidget(entryIndex, 2, cmp);
    }

    @Override
    public void resetSteps() {
        stepFieldTable.removeAllRows();
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

    private native void console(String text) /*-{
        console.log(text);
    }-*/;

}

