package aurora.client.view.editor.components;

import aurora.client.view.editor.components.buttons.ResetButton;
import aurora.client.view.editor.components.buttons.RunPauseContinueButton;
import aurora.client.view.editor.components.buttons.StepButton;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ActionBar extends Composite {
    interface ActionBarUiBinder extends UiBinder<Widget, ActionBar> {
    }

    private static ActionBarUiBinder ourUiBinder = GWT.create(ActionBarUiBinder.class);

    @UiField RunPauseContinueButton runPauseContinueButton;
    @UiField ResetButton resetButton;
    @UiField StepButton stepButton;


    public ActionBar() {

        initWidget(ourUiBinder.createAndBindUi(this));

    }


}