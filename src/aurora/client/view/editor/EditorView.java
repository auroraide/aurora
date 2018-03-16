package aurora.client.view.editor;

import aurora.backend.HighlightedLambdaExpression;
import aurora.backend.parser.Token;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.client.EditorDisplay;
import aurora.client.event.ErrorDisplayedEvent;
import aurora.client.event.ExportLaTeXEvent;
import aurora.client.event.FinishFinishEvent;
import aurora.client.event.ResultCalculatedEvent;
import aurora.client.event.ShareLinkEvent;
import aurora.client.event.ViewStateChangedEvent;
import aurora.client.view.editor.actionbar.ActionBar;
import aurora.client.view.popup.InfoDialogBox;
import aurora.client.view.popup.ShareDialogBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Widget;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This is where the user may view and manipulate code.
 * Three different kinds of code fields are provided.
 * An input field, which can be viewed and edited, as well as steps and an output field which can only be viewed.
 */
public class EditorView extends Composite implements EditorDisplay {
    interface EditorUiBinder extends UiBinder<Widget, EditorView> {
    }

    private static final EditorUiBinder ourUiBinder = GWT.create(EditorUiBinder.class);

    private CodeMirrorPanel inputCodeMirror;
    private CodeMirrorPanel outputCodeMirror;
    private Map<Integer, HighlightedLambdaExpression> stepMap;
    private Button inputOptionButton;
    private Button outputOptionButton;
    private InfoDialogBox infoDialogBox;
    private InfoDialogBox errorMessageDialogBox;
    private ShareDialogBox shareLaTexSnippetDialogBox;
    private EventBus eventBus;

    // Input Field
    @UiField
    DockLayoutPanel inputDockLayoutPanel;
    @UiField
    ActionBar actionBar;

    // Step Field
    @UiField
    FlexTable stepFieldTable;

    // Output Field
    @UiField
    DockLayoutPanel outputDockLayoutPanel;

    /**
     * Creates the EditorView contents and adds them to their respective parts of the window.
     *
     * @param eventBus Current {@link EventBus} instance.
     */
    public EditorView(EventBus eventBus) {
        this.eventBus = eventBus;
        initWidget(ourUiBinder.createAndBindUi(this));
        this.shareLaTexSnippetDialogBox = new ShareDialogBox("");
        this.errorMessageDialogBox = new InfoDialogBox();
        setupInputField();
        setupOutputField();
        this.stepMap = new HashMap<Integer, HighlightedLambdaExpression>();
        setupInfoDialogBox();
        stepFieldTable.setSize("100%", "100%");
        this.actionBar.setDefaultStateAppearance();
        eventWiring();
    }
    
    private void eventWiring() {
        eventListeningActionbar();
    }

    private void eventListeningActionbar() {

        eventBus.addHandler(ViewStateChangedEvent.TYPE, viewStateChangedEvent -> {
            switch (viewStateChangedEvent.getViewState()) {
                case DEFAULT_STATE:
                    EditorView.this.actionBar.setDefaultStateAppearance();
                    break;
                case RUNNING_STATE:
                    EditorView.this.actionBar.setRunningStateAppearance();
                    break;
                case PAUSED_STATE:
                    EditorView.this.actionBar.setPausedStateAppearance();
                    break;
                case STEP_BEFORE_RESULT_STATE:
                    EditorView.this.actionBar.setStepBeforeResultAppearance();
                    break;
                case FINISHED_STATE:
                    EditorView.this.actionBar.setFinishedStateAppearance();
                    break;
                default:
                    // In FINISHED_FINISHED_STATE
                    EditorView.this.actionBar.setFinishedFinishedAppearance();
            }

        });
    }

    private void setupInputField() {
        this.inputOptionButton = new Button(" ");
        // TODO Set styling for optionButton
        inputOptionButton.addStyleName("outputShare");
        MenuBar optionsMenu = new MenuBar(true);
        optionsMenu.addItem("options", setupInputMenuBar());
        MenuBar debugMenu = new MenuBar(true);
        debugMenu.addItem("debug", setupInputMenuBarDEBUG());
        this.inputDockLayoutPanel.addWest(optionsMenu, 4);
        this.inputDockLayoutPanel.addWest(debugMenu, 4);

        this.inputCodeMirror = new CodeMirrorPanel();
        this.inputDockLayoutPanel.add(this.inputCodeMirror);
        this.inputDockLayoutPanel.setSize("100%", "100%");

        Scheduler.get().scheduleDeferred((Command) () -> {
            inputCodeMirror.setOption("theme", "material");
            //autofocus not working???
            inputCodeMirror.setOption("autofocus", true);
            inputCodeMirror.setOption("mode", "aurorascript");
            inputCodeMirror.setOption("autoCloseBrackets", true);
            inputCodeMirror.setOption("matchBrackets", true);
            inputCodeMirror.setOption("styleActiveLine", true);
            inputCodeMirror.setOption("back2Lambda", null);
        });
    }

    private MenuBar setupInputMenuBar() {
        MenuBar optionsMenuBar = new MenuBar(true);
        optionsMenuBar.addItem("toggle VIM", (Command) () -> {
            if (inputCodeMirror.getOption("keyMap").equals("default")) {
                inputCodeMirror.setOption("keyMap", "vim");
            } else {
                inputCodeMirror.setOption("keyMap", "default");
            }
        });

        optionsMenuBar.addItem("LaTeX", (Command) () ->
                EditorView.this.eventBus.fireEvent(new ExportLaTeXEvent(ExportLaTeXEvent.INPUT)));
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
                infoDialogBox.setTitle("This is an error");
                infoDialogBox.setDescription("42");
                infoDialogBox.show();
            }
        });

        debugMenuBar.addItem("Highlight", new Command() {
            public void execute() {
                getTokenPosition((CodeMirrorPanel) stepFieldTable.getWidget(0, 2), stepMap.get(1), 14);
            }
        });

        return debugMenuBar;
    }

    private void setupOutputField() {
        MenuBar shareMenuBar = createShareMenu("outputShare", "", ExportLaTeXEvent.RESULT);
        //this.outputOptionButton = new Button(""); TODO delete if not needed
        // TODO Set styling for optionButton
        //outputOptionButton.addStyleName("outputShare");
        //this.outputDockLayoutPanel.addWest(this.outputOptionButton, 4); // TODO delete if not needed
        this.outputDockLayoutPanel.addWest(shareMenuBar, 4);
        this.outputCodeMirror = new CodeMirrorPanel();
        this.outputDockLayoutPanel.add(this.outputCodeMirror);
        this.outputDockLayoutPanel.setSize("100%", "100%");

        Scheduler.get().scheduleDeferred((Command) () -> {
            outputCodeMirror.setOption("theme", "material");
            outputCodeMirror.setOption("readOnly", true);
            outputCodeMirror.setOption("mode", "aurorascript");
            outputCodeMirror.setOption("matchBrackets", true);
        });
    }

    private MenuBar createShareMenu(String shareMenuStyleName, String optionstyleName, int index) {
        MenuBar shareMenu = new MenuBar(true);
        MenuBar options = new MenuBar(true);

        // TODO Set styling for option button
        //shareMenu.addStyleName(shareMenuStyleName);
        //optionMenu.addStyleName(optionsStyleName);
        options.addItem("LaTeX", (Command) () -> EditorView.this.eventBus.fireEvent(new ExportLaTeXEvent(index)));
        options.addItem("Link", (Command) () -> EditorView.this.eventBus.fireEvent(new ShareLinkEvent(index)));
        shareMenu.addItem("option", options);

        return shareMenu;
    }

    private void setupInfoDialogBox() {
        this.infoDialogBox = new InfoDialogBox();    
    }

    @Override
    public void displaySyntaxError(SyntaxException syntaxException) {
        this.errorMessageDialogBox.setDescription("Syntax error detected at line " + syntaxException.getLine()
                + " and column " + syntaxException.getColumn() + ".");
        this.errorMessageDialogBox.show();
        Scheduler.get().scheduleDeferred((Command) () -> {
            EditorView.this.eventBus.fireEvent(new ErrorDisplayedEvent());
        });

    }

    @Override
    public void displaySemanticError(SemanticException semanticException) {
        this.errorMessageDialogBox.setDescription("Semantic error detected at line " + semanticException.getLine()
                + " and column " + semanticException.getColumn() + ".");
        this.errorMessageDialogBox.show();
        Scheduler.get().scheduleDeferred((Command) () -> {
            EditorView.this.eventBus.fireEvent(new ErrorDisplayedEvent());
        });
    }

    @Override
    public String getInput() {
        return this.inputCodeMirror.getValue().replace('λ', '\\');
    }

    @Override
    public void addNextStep(List<HighlightedLambdaExpression> highlightedLambdaExpressions, int index) {
        for (int i = 0; i < highlightedLambdaExpressions.size(); i++) {
            stepMap.put(index + i, highlightedLambdaExpressions.get(i));
            addStepEntry(stepFieldTable.getRowCount(), index + i, highlightedLambdaExpressions.get(i));
        }
    }

    private void addStepEntry(int entryIndex, int visibleIndex, HighlightedLambdaExpression hle) {
        stepFieldTable.setText(entryIndex, 0, Integer.toString(visibleIndex));
        // TODO set shareMenu Style and optionMenuStyle
        stepFieldTable.setWidget(entryIndex,1, createShareMenu("", "", visibleIndex));
        CodeMirrorPanel cmp = new CodeMirrorPanel();

        //TODO: once hle is done, use its magic
        Scheduler.get().scheduleDeferred((Command) () -> {
            cmp.setValue(hle.toString());
            cmp.setOption("readOnly", true);
            cmp.setOption("mode", "aurorascript");
            cmp.setOption("matchBrackets", true);
            cmp.setOption("lineNumbers", false);
            cmp.setOption("theme", "material");
        });
        stepFieldTable.setWidget(entryIndex, 2, cmp);
    }

    /**
     * Highlights content between two tokens from given step.
     *
     * @param stepNumber step to highlight.
     * @param startToken index of first highlighted token.
     * @param endToken index of last highlighted token.
     */
    public void highlightStep(int stepNumber, int startToken, int endToken) {
        CodeMirrorPanel cm = (CodeMirrorPanel) stepFieldTable.getWidget(stepNumber, 2);
        HighlightedLambdaExpression hle = stepMap.get(stepNumber);
        int[] fromPos = getTokenPosition(cm, hle, startToken);
        int[] toPos = getTokenPosition(cm, hle, endToken);
        cm.markText(fromPos[0], fromPos[1], toPos[0], toPos[1], "#ff0");
    }

    private int[] getTokenPosition(CodeMirrorPanel cm, HighlightedLambdaExpression hle, int index) {
        GWT.log("getTokenPosition");
        int tokenPosition = 0;
        for (Token token: hle) {
            if (token.getOffset() == index) {
                break;
            }
            tokenPosition += token.toString().length();
        }
        
        int line = 0;
        int ch = 0;
        for (int i = 0; i < cm.lineCount(); ++i) {
            if (tokenPosition >= cm.getLine(i).length()) {
                tokenPosition -= cm.getLine(i).length();
            } else {
                ch = tokenPosition;
                break;
            }
        }
        GWT.log(String.valueOf(line) + "  " + String.valueOf(ch));
        cm.markText(0, 1, 0, 5, "#ff0");
        return new int[] {line, ch};
    }

    //TODO:remove once hle is done
    private void addNextStepDEBUG(List<String> highlightedLambdaExpressions) {
        highlightedLambdaExpressions.forEach((hle) -> {
            addStepEntryDEBUG(stepFieldTable.getRowCount(), hle);
        });
    }

    //TODO:remove once hle is done
    private void addStepEntryDEBUG(int entryIndex, String notAnHle) {
        stepFieldTable.setText(entryIndex, 0, Integer.toString(entryIndex));
        stepFieldTable.setWidget(entryIndex, 1, new Button(""));
        CodeMirrorPanel cmp = new CodeMirrorPanel();

        Scheduler.get().scheduleDeferred(new Command() {
            public void execute() {
                cmp.setValue(notAnHle);
                cmp.setOption("theme", "material");
                cmp.setOption("readOnly", true);
                cmp.setOption("mode", "aurorascript");
                cmp.setOption("matchBrackets", true);
            }
        });
        stepFieldTable.setWidget(entryIndex, 2, cmp);
    }

    @Override
    public void resetSteps() {
        stepMap.clear();
        stepFieldTable.removeAllRows();
    }

    @Override
    public void resetResult() {
        Scheduler scheduler = Scheduler.get();
        scheduler.scheduleDeferred((Command) () -> EditorView.this.outputCodeMirror.setValue(""));
    }

    @Override
    public void finishedFinished(HighlightedLambdaExpression result) {
        Scheduler scheduler = Scheduler.get();
        scheduler.scheduleDeferred((Command) () -> EditorView.this.eventBus.fireEvent(new FinishFinishEvent()));
        this.outputCodeMirror.setValue(result.toString().replace('\\', 'λ'));
    }

    @Override
    public void displayResult(HighlightedLambdaExpression highlightedLambdaExpression) {
        this.outputCodeMirror.setValue(highlightedLambdaExpression.toString().replace('\\', 'λ'));

        Scheduler scheduler = Scheduler.get();
        scheduler.scheduleDeferred((Command) () -> EditorView.this.eventBus.fireEvent(new ResultCalculatedEvent()));
        // TODO Remove log, when not needed anymore
        GWT.log("View should display HLE: " + highlightedLambdaExpression.toString());
    }

    @Override
    public void setInput(HighlightedLambdaExpression highlightedLambdaExpression) {
        this.inputCodeMirror.setValue(highlightedLambdaExpression.toString());
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

