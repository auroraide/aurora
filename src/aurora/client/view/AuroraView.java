package aurora.client.view;

import aurora.client.Aurora;
import aurora.client.AuroraDisplay;
import aurora.client.EditorDisplay;
import aurora.client.SidebarDisplay;
import aurora.client.event.ContinueEvent;
import aurora.client.event.ErrorDisplayedEvent;
import aurora.client.event.FinishFinishEvent;
import aurora.client.event.PauseEvent;
import aurora.client.event.ReStepEvent;
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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Command;
import com.google.gwt.core.client.Scheduler;


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

        /*currentState.stepBtnClicked();
        currentState.stateTransition();

        currentState.resultCalculated();
        currentState.stateTransition();

        currentState.resetBtnClicked();
        currentState.stateTransition();

        currentState.stepBtnClicked();
        currentState.stateTransition();*/
    }

    @Override
    public void displayLatexSnippetDialog(String latexCode) {
        this.latexDialogBox.setShareText(latexCode);
        this.latexDialogBox.show();
    }

    @Override
    public void displayShortLinkDialog(String shortLink) {
        this.shortLinkDialogBox.setShareText(shortLink);
        this.shortLinkDialogBox.show();
    }

    @Override
    public void setStepNumber(int stepNumber) {

    }

    private void eventWiring() {
        wireStateMachine();
    }

    private HandlerRegistration getRunHotkey() {
        return Event.addNativePreviewHandler(event -> {
            NativeEvent nativeEvent = event.getNativeEvent();
            //Return key has keyCode 13
            if (nativeEvent.getCtrlKey() && nativeEvent.getKeyCode() == 13){
                nativeEvent.preventDefault();
                if(nativeEvent.getType().equals("keyup")) {
                    Scheduler.get().scheduleDeferred((Command) () -> {
                        GWT.log("Hotkey pressed for run");
                        AuroraView.this.currentState.runBtnClicked();
                        AuroraView.this.currentState.stateTransition();
                    });
                }
            }
        });    
    }

    private HandlerRegistration getPauseHotkey() {
        return Event.addNativePreviewHandler(event -> {
            NativeEvent nativeEvent = event.getNativeEvent();
            //Space key has keyCode 32
            if (nativeEvent.getCtrlKey() && nativeEvent.getKeyCode() == 32){
                nativeEvent.preventDefault();
                if(nativeEvent.getType().equals("keyup")) {
                    Scheduler.get().scheduleDeferred((Command) () -> {
                        GWT.log("Hotkey pressed for pause");
                        AuroraView.this.currentState.pauseBtnClicked();
                        AuroraView.this.currentState.stateTransition();
                    });
                }
            }
        });
    }

    private HandlerRegistration getResetHotkey() {
        return Event.addNativePreviewHandler(event -> {
            NativeEvent nativeEvent = event.getNativeEvent();
            //Backspace key has keyCode 8 
            if (nativeEvent.getCtrlKey() && nativeEvent.getKeyCode() == 8){
                nativeEvent.preventDefault();
                if(nativeEvent.getType().equals("keyup")) {
                    Scheduler.get().scheduleDeferred((Command) () -> {
                        GWT.log("Hotkey pressed for reset");
                        AuroraView.this.currentState.resetBtnClicked();
                        AuroraView.this.currentState.stateTransition();
                    });
                }
            }
        });
    }

    private HandlerRegistration getStepHotkey() {
        return Event.addNativePreviewHandler(event -> {
            NativeEvent nativeEvent = event.getNativeEvent();
            if (nativeEvent.getCtrlKey() && nativeEvent.getShiftKey()){
                nativeEvent.preventDefault();
                Scheduler.get().scheduleDeferred((Command) () -> {
                    GWT.log("Hotkey pressed for reset");
                    AuroraView.this.currentState.resetBtnClicked();
                    AuroraView.this.currentState.stateTransition();
                });
            }
        });
    
    }

    private void wireStateMachine() {
        this.editor.getActionBar().getRunButton().addClickHandler(event -> {
            AuroraView.this.currentState.runBtnClicked();
            AuroraView.this.currentState.stateTransition();
        });

        HandlerRegistration logHandlerRun = getRunHotkey();

        this.editor.getActionBar().getPauseButton().addClickHandler(event -> {
            AuroraView.this.currentState.pauseBtnClicked();
            AuroraView.this.currentState.stateTransition();
        });

        HandlerRegistration logHandlerPause = getPauseHotkey();

        this.editor.getActionBar().getContinueButton().addClickHandler(event -> {
            AuroraView.this.currentState.continueBtnClicked();
            AuroraView.this.currentState.stateTransition();
        });

        this.editor.getActionBar().getResetButton().addClickHandler(event -> {
            AuroraView.this.currentState.resetBtnClicked();
            AuroraView.this.currentState.stateTransition();
        });
        
        HandlerRegistration logHandlerReset = getResetHotkey();

        this.editor.getActionBar().getStepButton().addClickHandler(event -> {
            AuroraView.this.currentState.stepBtnClicked();
            AuroraView.this.currentState.stateTransition();
        });

        HandlerRegistration logHandlerStep = getStepHotkey();

        this.eventBus.addHandler(ResultCalculatedEvent.TYPE, event -> {
            AuroraView.this.currentState.resultCalculated();
            AuroraView.this.currentState.stateTransition();
        });

        this.eventBus.addHandler(FinishFinishEvent.TYPE, event -> {
            AuroraView.this.currentState.finishedFinished();
            AuroraView.this.currentState.stateTransition();
        });

        this.eventBus.addHandler(ErrorDisplayedEvent.TYPE, event -> {
            AuroraView.this.currentState.errorDisplayed();
            AuroraView.this.currentState.stateTransition();
        });

        // TODO Wire RedexClickedEvent in AuroraView
        /*this.eventBus.addHandler(RedexClickedEvent.TYPE, redexClickedEvent -> {
            AuroraView.this.currentState.redexClicked();
            AuroraView.this.currentState.stateTransition();
        });*/
    }



    public EditorDisplay getEditor() {
        return this.editor;
    }

    public SidebarDisplay getSidebar() {
        return this.sidebar;
    }

    private class DefaultState extends State {


        @Override
        protected void errorDisplayed() {
            GWT.log("DefaultState.errorDisplayed()");
            throw new IllegalStateException("Executing errorDisplayed is not allowed in DefaultState!");
        }

        @Override
        protected void runBtnClicked() {
            GWT.log("DefaultState.runBtnClicked()");
            this.state = AuroraView.this.runningState;
            AuroraView.this.eventBus.fireEvent(new RunEvent());
        }

        @Override
        protected void pauseBtnClicked() {
            GWT.log("Executing pauseBtnClicked is not allowed in DefaultState!");
            throw new IllegalStateException("Executing pauseBtnClicked is not allowed in DefaultState!");
        }

        @Override
        protected void resetBtnClicked() {
            GWT.log("Executing resetBtnClicked is not allowed in DefaultState!");
            throw new IllegalStateException("Executing resetBtnClicked is not allowed in DefaultState!");
        }

        @Override
        protected void continueBtnClicked() {
            GWT.log("Executing continueBtnClicked is not allowed in DefaultState!");
            throw new IllegalStateException("Executing continueBtnClicked is not allowed in DefaultState!");
        }

        @Override
        protected void stepBtnClicked() {
            GWT.log("DefaultState.stepBtnClicked()");
            this.state = AuroraView.this.stepBeforeResultState;
            AuroraView.this.eventBus.fireEvent(new StepEvent());
        }

        @Override
        protected void resultCalculated() {
            GWT.log("Executing resultCalculated is not allowed in DefaultState!");
            throw new IllegalStateException("Executing resultCalculated is not allowed in DefaultState!");
        }

        @Override
        protected void redexClicked() {
            GWT.log("DefaultState.redexClicked()");
            // TODO give parameter
            //AuroraView.this.eventBus.fireEvent(new RedexClickedEvent());
            this.state = AuroraView.this.stepBeforeResultState;
        }

        @Override
        protected void finishedFinished() {
            GWT.log("Executing finishedFinished is not allowed in DefaultState!");
            throw new IllegalStateException("Executing finishedFinished is not allowed in StepBeforeResultState!");
        }

        @Override
        protected void onEntry() {
            GWT.log("DefaultState.onEntry()");
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.DEFAULT_STATE));
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected State next() {
            GWT.log("DefaultState.next()");
            return this.state;
        }
    }

    private class FinishedState extends State {

        @Override
        protected void errorDisplayed() {
            throw new IllegalStateException("Executing errorDisplayed is not allowed in FinishedState!");
        }

        @Override
        protected void runBtnClicked() {
            throw new IllegalStateException("Executing runBtnClicked is not allowed in FinishedState");
        }

        @Override
        protected void pauseBtnClicked() {
            throw new IllegalStateException("Executing pauseBtnClicked is not allowed in FinishedState!");
        }

        @Override
        protected void resetBtnClicked() {
            GWT.log("FinishedState.resetBtnClicked()");
            this.state = AuroraView.this.defaultState;
            AuroraView.this.eventBus.fireEvent(new ResetEvent());
        }

        @Override
        protected void continueBtnClicked() {
            throw new IllegalStateException("Executing continueBtnClicked is not allowed in FinishedState!");
        }

        @Override
        protected void stepBtnClicked() {
            GWT.log("FinishedState.stepBtnClicked()");
            this.state = AuroraView.this.finishedState;
            AuroraView.this.eventBus.fireEvent(new ReStepEvent());
        }

        @Override
        protected void resultCalculated() {
            throw new IllegalStateException("Executing resultCalculated is not allowed in FinishedState!");
        }

        @Override
        protected void redexClicked() {
            GWT.log("FinishedState.redexClicked()");
            this.state = AuroraView.this.finishedState;
            // TODO fire redexclicked event
        }

        @Override
        protected void finishedFinished() {
            GWT.log("FinishedState.finishedFinished()");
            this.state = AuroraView.this.finishedFinishedState;
        }

        @Override
        protected void onEntry() {
            GWT.log("FinishedState.onEntry()");
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.FINISHED_STATE));
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected State next() {
            GWT.log("FinishedState.next()");
            return this.state;
        }
    }

    private class PausedState extends State {

        @Override
        protected void errorDisplayed() {
            throw new IllegalStateException("Executing errorDisplayed is not allowed in PausedState!");
        }

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
            GWT.log("PausedState.resetBtnClicked()");
            this.state = AuroraView.this.defaultState;
            AuroraView.this.eventBus.fireEvent(new ResetEvent());
        }

        @Override
        protected void continueBtnClicked() {
            GWT.log("PausedState.continueBtnClicked");
            this.state = AuroraView.this.runningState;
            AuroraView.this.eventBus.fireEvent(new ContinueEvent());
        }

        @Override
        protected void stepBtnClicked() {
            GWT.log("PausedState.stepBtnClicked()");
            this.state = AuroraView.this.pausedState;
            AuroraView.this.eventBus.fireEvent(new StepEvent());
        }

        @Override
        protected void resultCalculated() {
            GWT.log("PausedState.resultCalculated()");
            this.state = AuroraView.this.finishedState;
        }

        @Override
        protected void redexClicked() {
            GWT.log("PausedState.redexClicked()");
            this.state = AuroraView.this.pausedState;
            // TODO fire RedexClickedEvent
        }

        @Override
        protected void finishedFinished() {
            throw new IllegalStateException("Executing finishedFinished is not allowed in PauseState!");
        }

        @Override
        protected void onEntry() {
            GWT.log("PausedState.onEntry()");
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.PAUSED_STATE));
        }

        @Override
        protected void onExit() {
        }

        @Override
        protected State next() {
            GWT.log("PausedState.next()");
            return this.state;
        }
    }

    private class RunningState extends State {

        @Override
        protected void errorDisplayed() {
            GWT.log("RunningState.errorDisplayed()");
            this.state = AuroraView.this.defaultState;
        }

        @Override
        protected void runBtnClicked() {
            throw new IllegalStateException("Executing runBtnClicked is not allowed in RunningState!");
        }

        @Override
        protected void pauseBtnClicked() {
            GWT.log("RunningState.pauseBtnClicked()");
            this.state = AuroraView.this.pausedState;
            AuroraView.this.eventBus.fireEvent(new PauseEvent());
        }

        @Override
        protected void resetBtnClicked() {
            GWT.log("RunningState.resetBtnClicked()");
            this.state = AuroraView.this.defaultState;
            AuroraView.this.eventBus.fireEvent(new ResetEvent());
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
            GWT.log("RunningState.resultCalculated()");
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
            GWT.log("RunningState.onEntry()");
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.RUNNING_STATE));
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected State next() {
            GWT.log("RunningState.next()");
            return this.state;
        }
    }

    private class StepBeforeResultState extends State {

        @Override
        protected void errorDisplayed() {
            GWT.log("StepBeforeResultState.errorDisplayed()");
            this.state = AuroraView.this.defaultState;
        }

        @Override
        protected void runBtnClicked() {
            GWT.log("StepBeforeResultState.runBtnClicked()");
            this.state = AuroraView.this.runningState;
            AuroraView.this.eventBus.fireEvent(new RunEvent());
        }

        @Override
        protected void pauseBtnClicked() {
            GWT.log("Executing pauseBtnClicked is not allowed in StepBeforeResultState!");
            throw new IllegalStateException("Executing pauseBtnClicked is not allowed in StepBeforeResultState!");
        }

        @Override
        protected void resetBtnClicked() {
            GWT.log("StepBeforeResultState.resetBtnClicked()");
            this.state = AuroraView.this.defaultState;
            AuroraView.this.eventBus.fireEvent(new ResetEvent());
        }

        @Override
        protected void continueBtnClicked() {
            GWT.log("Executing continueBtnClicked is not allowed in StepBeforeResultState!");
            throw new IllegalStateException("Executing continueBtnClicked is not allowed in StepBeforeResultState!");
        }

        @Override
        protected void stepBtnClicked() {
            GWT.log("StepBeforeResultState.stepBtnClicked()");
            this.state = AuroraView.this.stepBeforeResultState;
            AuroraView.this.eventBus.fireEvent(new StepEvent());
        }

        @Override
        protected void resultCalculated() {
            GWT.log("StepBeforeResultState.resultCalculated()");
            this.state = AuroraView.this.finishedFinishedState;
        }

        @Override
        protected void redexClicked() {
            GWT.log("StepBeforeResultState.redexClicked()");
            this.state = AuroraView.this.stepBeforeResultState;
            // TODO fire RedexClickedEvent
        }

        @Override
        protected void finishedFinished() {
            GWT.log("Executing finishedBtnClicked is not allowed in StepBeforeResultState!");
            throw new IllegalStateException("Executing finishedFinished is not allowed in StepBeforeResultState!");
        }

        @Override
        protected void onEntry() {
            GWT.log("StepBeforeResultState.onEntryCalled()");
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.STEP_BEFORE_RESULT_STATE));
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected State next() {
            GWT.log("StepBeforeResultState.next()");
            return this.state;
        }
    }

    private class FinishedFinishedState extends State {

        @Override
        protected void errorDisplayed() {
            throw new IllegalStateException("Executing errorDisplayed is not allowed in FinishedFinishedState!");
        }

        @Override
        protected void runBtnClicked() {
            GWT.log("Executing runBtnClicked is not allowed in FinishedFinishedState!");
            throw new IllegalStateException("Executing runBtnClicked is not allowed in FinishedFinishedState!");
        }

        @Override
        protected void pauseBtnClicked() {
            GWT.log("Executing pauseBtnClicked is not allowed in FinishedFinishedState!");
            throw new IllegalStateException("Executing pauseBtnClicked is not allowed in FinishedFinishedState!");
        }

        @Override
        protected void resetBtnClicked() {
            GWT.log("FinishedFinishedState.resetBtnClicked()");
            this.state = AuroraView.this.defaultState;
            AuroraView.this.eventBus.fireEvent(new ResetEvent());
        }

        @Override
        protected void continueBtnClicked() {
            GWT.log("Executing continueBtnClicked is not allowed in FinishedFinishedState!");
            throw new IllegalStateException("Executing continueBtnClicked is not allowed in FinishedFinishedState!");
        }

        @Override
        protected void stepBtnClicked() {
            GWT.log("Executing stepBtnClicked is not allowed in FinishedFinishedState!");
            throw new IllegalStateException("Executing stepBtnClicked is not allowed in FinishedFinishedState!");
        }

        @Override
        protected void resultCalculated() {
            GWT.log("Executing resultBtnClicked is not allowed in FinishedFinishedState!");
            throw new IllegalStateException("Executing resultCalculated is not allowed in FinishedFinishedState!");
        }

        @Override
        protected void redexClicked() {
            GWT.log("Executing redexBtnClicked is not allowed in FinishedFinishedState!");
            throw new IllegalStateException("Executing redexClicked is not allowed in FinishedFinishedState!");
        }

        @Override
        protected void finishedFinished() {
            GWT.log("Executing finishedFinished is not allowed in FinishedFinishedState!");
            throw new IllegalStateException("Executing finishedFinished is not allowed in FinishedFinishedState!");
        }

        @Override
        protected void onEntry() {
            GWT.log("FinishedFinishedState.onEntry()");
            AuroraView.this.eventBus.fireEvent(new ViewStateChangedEvent(ViewState.FINISHED_FINISHED_STATE));
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected State next() {
            GWT.log("FinishedFinishedState.next()");
            return this.state;
        }
    }

    private abstract class State {
        protected State state;

        protected abstract void errorDisplayed();

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
            State next = next();

            if (next != this) {
                this.onExit();
                next.onEntry();
                AuroraView.this.currentState = next;
                GWT.log(next.getClass().getName());
            }
        }

    }
}
