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

    /**
     * Represents the three types of button a runPauseContinueButton can have.
     */
    public enum ButtonType {
        RUN, PAUSE, CONTINUE
    }

    @UiField
    Button runPauseContinueButton;
    @UiField
    Button resetButton;
    @UiField
    Button stepButton;

    private ButtonType rpcButtonActive;

    /**
     * Generates a new ActionBar.
     */
    public ActionBar() {
        this.rpcButtonActive = ButtonType.RUN;
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /**
     * Getter for runPauseContinueButton.
     *
     * @return runPauseContinueButton
     */
    public Button getRunPauseContinueButton() {
        return runPauseContinueButton;
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
     * Holds the information, whether a run, pause or continue button is active and displayed in ActionBar.
     *
     * @return The type of  button which is currently active and displayed.
     */
    public ButtonType getRpcButtonActive() {
        return rpcButtonActive;
    }

    interface ActionBarUiBinder extends UiBinder<Widget, ActionBar> {
    }
}
