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
    Button runPauseContinueButton;
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

    interface ActionBarUiBinder extends UiBinder<Widget, ActionBar> {
    }
}
