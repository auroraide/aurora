package aurora.client.view;

import aurora.client.AuroraDisplay;
import aurora.client.EditorDisplay;
import aurora.client.SidebarDisplay;
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
        this.stepBeforeResultState = new StepBeforeResultState();
        this.currentState = defaultState;


        // editor.getActionBar().onRunButtonClick(e -> {
        //     currentState = currentState.run();
        //     eventBus.fire(new RunEvent());
        // });
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

    private void bind() {
        // on click share latex (ist in sidebar):
        //      eventBus.fireEvent(new ShareLatexenvetdings(editor.balbalbalba))

        // on click share latex in step (ist in editor):
        //      eventBus.fireEvent(new ShareLatexenvetdings(editor.step[i].balbalbalba))

        // on step number change IN SIDEBAR:
        //      editor.stepnumber = ...;
        //      eventBus.fireevent(step chagned....)

        // on step number change IN EDITOR:
        //      sidebar.stepnumber = ...;
        //      eventBus.fireevent(step chagned....)
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
            this.state = AuroraView.this.stepBeforeResultState;
        }

        @Override
        protected void resultCalculated() {
            throw new IllegalStateException("Executing resultCalculated is not allowed in DefaultState!");
        }

        @Override
        protected void redexClicked() {
            this.state = AuroraView.this.stepBeforeResultState;
        }

        @Override
        protected void onEntry() {
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.DEFAULT_STATE));
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected State next() {
            return this.state;
        }
    }

    private class FinishedState extends State {

        @Override
        protected void runBtnClicked() {
            this.state = AuroraView.this.runningState;
        }

        @Override
        protected void pauseBtnClicked() {
            throw new IllegalStateException("Executing pauseBtnClicked is not allowed FinishedState!");
        }

        @Override
        protected void resetBtnClicked() {
            this.state = AuroraView.this.defaultState;
        }

        @Override
        protected void continueBtnClicked() {
            throw new IllegalStateException("Executing continueBtnClicked is not allowed FinishedState!");
        }

        @Override
        protected void stepBtnClicked() {
            throw new IllegalStateException("Executing stepBtnClicked is not allowed FinishedState!");
        }

        @Override
        protected void resultCalculated() {
            throw new IllegalStateException("Executing resultCalculated is not allowed FinishedState!");
        }

        @Override
        protected void redexClicked() {
            throw new IllegalStateException("Executing redexClicked is not allowed FinishedState!");
        }

        @Override
        protected void onEntry() {
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.FINISHED_STATE));
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected State next() {
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
            this.state = AuroraView.this.defaultState;
        }

        @Override
        protected void continueBtnClicked() {
            this.state = AuroraView.this.runningState;
        }

        @Override
        protected void stepBtnClicked() {
            this.state = AuroraView.this.pausedState;
        }

        @Override
        protected void resultCalculated() {
            this.state = AuroraView.this.finishedState;
        }

        @Override
        protected void redexClicked() {
            this.state = AuroraView.this.pausedState;
        }

        @Override
        protected void onEntry() {
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.PAUSED_STATE));
        }

        @Override
        protected void onExit() {
        }

        @Override
        protected State next() {
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
            this.state = AuroraView.this.pausedState;
        }

        @Override
        protected void resetBtnClicked() {
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
            this.state = AuroraView.this.finishedState;
        }

        @Override
        protected void redexClicked() {
            throw new IllegalStateException("Executing redexClicked is not allowed in RunningState!");
        }

        @Override
        protected void onEntry() {
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.RUNNING_STATE));
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected State next() {
            return this.state;
        }
    }

    private class StepBeforeResultState extends State {

        @Override
        protected void runBtnClicked() {
            this.state = AuroraView.this.runningState;
        }

        @Override
        protected void pauseBtnClicked() {
            throw new IllegalStateException("Executing pauseBtnClicked is not allowed in StepBeforeResultState!");
        }

        @Override
        protected void resetBtnClicked() {
            this.state = AuroraView.this.defaultState;
        }

        @Override
        protected void continueBtnClicked() {
            throw new IllegalStateException("Executing continueBtnClicked is not allowed in StepBeforeResultState!");
        }

        @Override
        protected void stepBtnClicked() {
            this.state = AuroraView.this.stepBeforeResultState;
        }

        @Override
        protected void resultCalculated() {
            this.state = AuroraView.this.finishedState;
        }

        @Override
        protected void redexClicked() {
            this.state = AuroraView.this.stepBeforeResultState;
        }

        @Override
        protected void onEntry() {
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.STEP_BEFORE_RESULT_STATE));
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected State next() {
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

        protected abstract void onEntry();

        protected abstract void onExit();

        protected abstract State next();

        protected final void stateTransition() {
            State next = next();

            if (next != this) {
                this.onExit();
                next.onEntry();
                AuroraView.this.currentState = next;
            }
        }

    }
}
