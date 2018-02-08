package aurora.client.view;

import aurora.client.Aurora;
import aurora.client.AuroraDisplay;
import aurora.client.EditorDisplay;
import aurora.client.SidebarDisplay;
import aurora.client.event.ContinueEvent;
import aurora.client.event.PauseEvent;
import aurora.client.event.RedexClickedEvent;
import aurora.client.event.ResetEvent;
import aurora.client.event.ResultCalculatedEvent;
import aurora.client.event.RunEvent;
import aurora.client.event.StepEvent;
import aurora.client.event.ViewStateChangedEvent;
import aurora.client.view.editor.EditorView;
import aurora.client.view.popup.ShareDialogBox;
import aurora.client.view.sidebar.SidebarView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


/**
 * Knows the layout of the component tree.
 * Receives "direct" events from actionbar and emits more semantic events onto the event bus.
 * Look into the aurora.client.event package.
 * TODO this doc is incomplete.
 */
public class AuroraView extends Composite implements AuroraDisplay {
    interface DesktopViewUiBinder extends UiBinder<Widget, AuroraView> {
    }

    private static DesktopViewUiBinder ourUiBinder = GWT.create(DesktopViewUiBinder.class);

    @UiField(provided = true)
    EditorView editor;
    @UiField(provided = true)
    SidebarView sidebar;

    private final EventBus eventBus;
    private final ShareDialogBox latexDialogBox;
    private final ShareDialogBox shortLinkDialogBox;

    private State defaultState;
    private State runningState;
    private State pausedState;
    private State finishedState;
    private State finishedFinishedState;
    private State stepBeforeResultState;

    private State currentState;

    /**
     * Constructor.
     *
     * @param eventBus Instance of current {@link EventBus}.
     * @param sidebar  Instance of {@link SidebarView}
     * @param editor   Instance of {@link EditorView}
     */
    public AuroraView(EventBus eventBus, SidebarView sidebar, EditorView editor) {
        this.editor = editor;
        this.sidebar = sidebar;
        this.eventBus = eventBus;
        initWidget(ourUiBinder.createAndBindUi(this));

        this.latexDialogBox = new ShareDialogBox("Share LaTeX");
        this.shortLinkDialogBox = new ShareDialogBox("Share short link");

        this.defaultState = new DefaultState();
        this.runningState = new RunningState();
        this.pausedState = new PausedState();
        this.finishedState = new FinishedState();
        this.finishedFinishedState = new FinishedFinishedState();
        this.stepBeforeResultState = new StepBeforeResultState();
        this.currentState = this.defaultState;

        eventWiring();

    }

    @Override
    public void displayLatexSnippetDialog(String latexCode) {

    }

    @Override
    public void displayShortLinkDialog(String shortLink) {

    }

    @Override
    public void setStepNumber(int stepNumber) {

    }

    private void eventWiring() {
        wireStateMachine();
    }

    private void wireStateMachine() {
        this.eventBus.addHandler(RunEvent.TYPE, event -> {
            AuroraView.this.currentState.runBtnClicked();
            AuroraView.this.currentState.stateTransition();
        });

        this.eventBus.addHandler(PauseEvent.TYPE, event -> {
            AuroraView.this.currentState.pauseBtnClicked();
            AuroraView.this.currentState.stateTransition();
        });

        this.eventBus.addHandler(ResetEvent.TYPE, event -> {
            AuroraView.this.currentState.resetBtnClicked();
            AuroraView.this.currentState.stateTransition();
        });

        this.eventBus.addHandler(ContinueEvent.TYPE, event -> {
            AuroraView.this.currentState.continueBtnClicked();
            AuroraView.this.currentState.stateTransition();
        });

        this.eventBus.addHandler(StepEvent.TYPE, event -> {
            AuroraView.this.currentState.stepBtnClicked();
            AuroraView.this.currentState.stateTransition();
        });

        this.eventBus.addHandler(RedexClickedEvent.TYPE, redexClickedEvent -> {
            AuroraView.this.currentState.redexClicked();
            AuroraView.this.currentState.stateTransition();
        });

        this.eventBus.addHandler(ResultCalculatedEvent.TYPE, event -> {
            AuroraView.this.currentState.resultCalculated();
            AuroraView.this.currentState.stateTransition();
        });
    }

    public EditorDisplay getEditor() {
        return this.editor;
    }

    public SidebarDisplay getSidebar() {
        return this.sidebar;
    }

    private class DefaultState extends State {


        @Override
        protected void runBtnClicked() {
            GWT.log("DEFAULT_STATE: runBtnClicked called.");
            this.state = AuroraView.this.runningState;
        }

        @Override
        protected void pauseBtnClicked() {
            throw new IllegalStateException("Executing pauseBtnClicked is not allowed in DefaultState!");
        }

        @Override
        protected void resetBtnClicked() {
            throw new IllegalStateException("Executing resetBtnClicked is not allowed in DefaultState!");
        }

        @Override
        protected void continueBtnClicked() {
            throw new IllegalStateException("Executing continueBtnClicked is not allowed in DefaultState!");
        }

        @Override
        protected void stepBtnClicked() {
            GWT.log("DEFAULT_STATE: stepBtnClicked called.");
            this.state = AuroraView.this.stepBeforeResultState;
        }

        @Override
        protected void resultCalculated() {
            throw new IllegalStateException("Executing resultCalculated is not allowed in DefaultState!");
        }

        @Override
        protected void redexClicked() {
            GWT.log("DEFAULT_STATE: redexClicked called.");
            this.state = AuroraView.this.stepBeforeResultState;
        }

        @Override
        protected void finishedFinished() {
            throw new IllegalStateException("Executing finishedFinished is not allowed in StepBeforeResultState!");
        }

        @Override
        protected void onEntry() {
            GWT.log("DEFAULT_STATE: onEntry called.");
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.DEFAULT_STATE));
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected State next() {
            GWT.log("DEFAULT_STATE: next called");
            return this.state;
        }
    }

    private class FinishedState extends State {

        @Override
        protected void runBtnClicked() {
            GWT.log("FINISHED_STATE: runBtnClicked called.");
            this.state = AuroraView.this.runningState;
        }

        @Override
        protected void pauseBtnClicked() {
            throw new IllegalStateException("Executing pauseBtnClicked is not allowed FinishedState!");
        }

        @Override
        protected void resetBtnClicked() {
            GWT.log("FINISHED_STATE: resetBtnClicked called.");
            this.state = AuroraView.this.defaultState;
        }

        @Override
        protected void continueBtnClicked() {
            throw new IllegalStateException("Executing continueBtnClicked is not allowed FinishedState!");
        }

        @Override
        protected void stepBtnClicked() {
            GWT.log("FINISHED_STATE: stepBtnClicked");
            this.state = AuroraView.this.finishedState;
        }

        @Override
        protected void resultCalculated() {
            throw new IllegalStateException("Executing resultCalculated is not allowed FinishedState!");
        }

        @Override
        protected void redexClicked() {
            GWT.log("FINISHED_STATE: redClicked");
            this.state = AuroraView.this.finishedState;
        }

        @Override
        protected void finishedFinished() {
            GWT.log("FINISHED_STATE: finishedFinished");
            this.state = AuroraView.this.finishedFinishedState;
        }

        @Override
        protected void onEntry() {
            GWT.log("FINISHED_STATE: onEntry called.");
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.FINISHED_STATE));
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected State next() {
            GWT.log("FinishedState: next called.");
            return this.state;
        }
    }

    private class PausedState extends State {

        @Override
        protected void runBtnClicked() {
            throw new IllegalStateException("Executing runBtnClicked is not allowed in PausedState!");
        }

        @Override
        protected void pauseBtnClicked() {
            throw new IllegalStateException("Executing pauseBtnClicked is not allowed in PausedState!");
        }

        @Override
        protected void resetBtnClicked() {
            GWT.log("PAUSED_STATE: resetBtnClicked called.");
            this.state = AuroraView.this.defaultState;
        }

        @Override
        protected void continueBtnClicked() {
            GWT.log("PAUSED_STATE: continueBtnClicked called.");
            this.state = AuroraView.this.runningState;
        }

        @Override
        protected void stepBtnClicked() {
            GWT.log("PAUSED_STATE: stepBtnClicked called.");
            this.state = AuroraView.this.pausedState;
        }

        @Override
        protected void resultCalculated() {
            GWT.log("PAUSED_STATE: resultCalculated called.");
            this.state = AuroraView.this.finishedState;
        }

        @Override
        protected void redexClicked() {
            GWT.log("PAUSED_STATE: redexClicked called.");
            this.state = AuroraView.this.pausedState;
        }

        @Override
        protected void finishedFinished() {
            throw new IllegalStateException("Executing finishedFinished is not allowed in PauseState!");
        }

        @Override
        protected void onEntry() {
            GWT.log("PAUSED_STATE: onEntry called.");
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.PAUSED_STATE));
        }

        @Override
        protected void onExit() {
        }

        @Override
        protected State next() {
            GWT.log("PAUSED_STATE: next called.");
            return this.state;
        }
    }

    private class RunningState extends State {

        @Override
        protected void runBtnClicked() {
            throw new IllegalStateException("Executing runBtnClicked is not allowed in RunningState!");
        }

        @Override
        protected void pauseBtnClicked() {
            GWT.log("RUNNING_STATE: pauseBtnClicked called.");
            this.state = AuroraView.this.pausedState;
        }

        @Override
        protected void resetBtnClicked() {
            GWT.log("RUNNING_STATE: resetBtnClicked called.");
            this.state = AuroraView.this.defaultState;
        }

        @Override
        protected void continueBtnClicked() {
            throw new IllegalStateException("Executing continueBtnClicked is not allowed in RunningState!");
        }

        @Override
        protected void stepBtnClicked() {
            throw new IllegalStateException("Executing stepBtnClicked is not allowed in RunningState!");
        }

        @Override
        protected void resultCalculated() {
            GWT.log("RUNNING_STATE: resultCalculated called.");
            this.state = AuroraView.this.finishedState;
        }

        @Override
        protected void redexClicked() {
            throw new IllegalStateException("Executing redexClicked is not allowed in RunningState!");
        }

        @Override
        protected void finishedFinished() {
            throw new IllegalStateException("Executing finishedFinished is not allowed in RunningState!\"");
        }

        @Override
        protected void onEntry() {
            GWT.log("RUNNING_STATE: onEntry called.");
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.RUNNING_STATE));
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected State next() {
            GWT.log("RUNNING_STATE: next called.");
            return this.state;
        }
    }

    private class StepBeforeResultState extends State {

        @Override
        protected void runBtnClicked() {
            GWT.log("STEP_BEFORE_RESULT: runBtnClicked called.");
            this.state = AuroraView.this.runningState;
        }

        @Override
        protected void pauseBtnClicked() {
            throw new IllegalStateException("Executing pauseBtnClicked is not allowed in StepBeforeResultState!");
        }

        @Override
        protected void resetBtnClicked() {
            GWT.log("STEP_BEFORE_RESULT: resetBtnClicked called.");
            this.state = AuroraView.this.defaultState;
        }

        @Override
        protected void continueBtnClicked() {
            throw new IllegalStateException("Executing continueBtnClicked is not allowed in StepBeforeResultState!");
        }

        @Override
        protected void stepBtnClicked() {
            GWT.log("STEP_BEFORE_RESULT: stepBtnClicked called.");
            this.state = AuroraView.this.stepBeforeResultState;
        }

        @Override
        protected void resultCalculated() {
            GWT.log("STEP_BEFORE_RESULT: resultCalculated called.");
            this.state = AuroraView.this.finishedState;
        }

        @Override
        protected void redexClicked() {
            GWT.log("STEP_BEFORE_RESULT: redexClicked called.");
            this.state = AuroraView.this.stepBeforeResultState;
        }

        @Override
        protected void finishedFinished() {
            throw new IllegalStateException("Executing finishedFinished is not allowed in StepBeforeResultState!");
        }

        @Override
        protected void onEntry() {
            GWT.log("STEP_BEFORE_RESULT: onEntryCalled.");
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.STEP_BEFORE_RESULT_STATE));
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected State next() {
            GWT.log("STEP_BEFORE_RESULT: next Called.");
            return this.state;
        }
    }

    private class FinishedFinishedState extends State {

        @Override
        protected void runBtnClicked() {
            throw new IllegalStateException("Executing runBtnClicked is not allowed in FinishedFinishedState!");
        }

        @Override
        protected void pauseBtnClicked() {
            throw new IllegalStateException("Executing pauseBtnClicked is not allowed in FinishedFinishedState!");
        }

        @Override
        protected void resetBtnClicked() {
            this.state = AuroraView.this.defaultState;
        }

        @Override
        protected void continueBtnClicked() {
            throw new IllegalStateException("Executing continueBtnClicked is not allowed in FinishedFinishedState!");
        }

        @Override
        protected void stepBtnClicked() {
            throw new IllegalStateException("Executing stepBtnClicked is not allowed in FinishedFinishedState!");
        }

        @Override
        protected void resultCalculated() {
            throw new IllegalStateException("Executing resultCalculated is not allowed in FinishedFinishedState!");
        }

        @Override
        protected void redexClicked() {
            throw new IllegalStateException("Executing redexClicked is not allowed in FinishedFinishedState!");
        }

        @Override
        protected void finishedFinished() {
            throw new IllegalStateException("Executing finishedFinished is not allowed in FinishedFinishedState!");
        }

        @Override
        protected void onEntry() {
            GWT.log("FINISHED_FINISHED_STATE: onEntryCalled.");
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.FINISHED_FINISHED_STATE));
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected State next() {
            GWT.log("FINISHED_FINISHED_STATE: next Called.");
            return this.state;
        }
    }

    private abstract class State {
        protected State state;

        protected abstract void runBtnClicked();

        protected abstract void pauseBtnClicked();

        protected abstract void resetBtnClicked();

        protected abstract void continueBtnClicked();

        protected abstract void stepBtnClicked();

        protected abstract void resultCalculated();

        protected abstract void redexClicked();

        protected abstract void finishedFinished();

        protected abstract void onEntry();

        protected abstract void onExit();

        protected abstract State next();

        protected final void stateTransition() {
            GWT.log("State transition ...");
            State next = next();

            if (next != this) {
                this.onExit();
                next.onEntry();
                AuroraView.this.currentState = next;
            }
        }

    }
}
