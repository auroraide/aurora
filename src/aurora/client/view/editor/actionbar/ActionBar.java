package aurora.client.view.editor.actionbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * The ActionBar provides Buttons to change the exectuion state of the current lambda expression.
 * This includes a run, pause, continue, as well as a reset button.
 * In addition a step Button is provided.
 */
public class ActionBar extends Composite {
    private static ActionBarUiBinder ourUiBinder = GWT.create(ActionBarUiBinder.class);

    @UiField
    Button runButton;
    @UiField
    Button pauseButton;
    @UiField
    Button continueButton;
    @UiField
    Button resetButton;
    @UiField
    Button stepButton;

    /**
     * Generates a new ActionBar.
     */
    public ActionBar() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /**
     * Getter for Run Button.
     *
     * @return run button
     */
    public Button getRunButton() {
        return runButton;
    }

    /**
     * Getter for Pause Button.
     *
     * @return pause button
     */
    public Button getPauseButton() {
        return pauseButton;
    }

    /**
     * Getter for Continue Button.
     *
     * @return continue button
     */
    public Button getContinueButton() {
        return continueButton;
    }

    /**
     * Getter for resetButton.
     *
     * @return resetButton
     */
    public Button getResetButton() {
        return resetButton;
    }

    /**
     * Getter for stepButton.
     *
     * @return stepButton
     */
    public Button getStepButton() {
        return stepButton;
    }

    /**
     * Sets the appearance of ActionBar's widgets to fit DEFAULT_STATE.
     */
    public void setDefaultStateAppearance() {
        this.runButton.setStyleName("hidden", false);
        this.continueButton.setStyleName("hidden", true);
        this.pauseButton.setStyleName("hidden", true);
        this.resetButton.setEnabled(false);
        this.stepButton.setEnabled(true);
    }

    /**
     * Sets the appearance of ActionBar's widgets to fit RUNNING_STATE.
     */
    public void setRunningStateAppearance() {
        this.runButton.setStyleName("hidden", true);
        this.continueButton.setStyleName("hidden", true);
        this.pauseButton.setStyleName("hidden", false);
        this.resetButton.setEnabled(true);
        this.stepButton.setEnabled(false);
    }

    /**
     * Sets the appearance of ActionBar's widgets to fit PAUSED_STATE.
     */
    public void setPausedStateAppearance() {
        this.runButton.setStyleName("hidden", true);
        this.continueButton.setStyleName("hidden", false);
        this.pauseButton.setStyleName("hidden", true);
        this.resetButton.setEnabled(true);
        this.stepButton.setEnabled(true);
    }

    /**
     * Sets the appearance of ActionBar's widgets to fit FINISHED_STATE.
     */
    public void setFinishedStateAppearance() {
        this.runButton.setStyleName("hidden", false);
        this.continueButton.setStyleName("hidden", true);
        this.pauseButton.setStyleName("hidden", true);
        this.resetButton.setEnabled(false);
        this.stepButton.setEnabled(false);
    }

    /**
     * Sets the appearance of ActionBar's widgets to fit STEP_BEFORE_RESULT_STATE.
     */
    public void  setStepBeforeResultAppearance() {
        this.runButton.setStyleName("hidden", false);
        this.continueButton.setStyleName("hidden", true);
        this.pauseButton.setStyleName("hidden", true);
        this.resetButton.setEnabled(true);
        this.stepButton.setEnabled(true);
    }

    interface ActionBarUiBinder extends UiBinder<Widget, ActionBar> {
    }
}
