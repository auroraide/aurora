package aurora.client.view.editor.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import aurora.client.view.editor.components.RPCButtonType;

public class ActionBar extends Composite {
    interface ActionBarUiBinder extends UiBinder<Widget, ActionBar> {
    }

    private static ActionBarUiBinder ourUiBinder = GWT.create(ActionBarUiBinder.class);

    @UiField Button runPauseContinueButton;
    @UiField Button resetButton;
    @UiField Button stepButton;


    public ActionBar() {
        initWidget(ourUiBinder.createAndBindUi(this));

    }

    public Button getRunPauseContinueButton() {
        return runPauseContinueButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public Button getStepButton() {
        return stepButton;
    }

    public void setStatusActiveInactiv(Button buttonToUpdate) {

    }
    public void setTypeOfRunPauseConcitueButton(RPCButtonType buttonType) {

    }
}