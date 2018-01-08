package aurora.client.view.editor.components.buttons;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

public class RunPauseContinueButton extends ActionButton {


    interface RunPauseContinueButtonUiBinder extends UiBinder<Widget, RunPauseContinueButton> {
    }

    private static RunPauseContinueButtonUiBinder ourUiBinder = GWT.create(RunPauseContinueButtonUiBinder.class);

    public RunPauseContinueButton() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    void onActionButtonClicked(ClickEvent event) {

    }

    public void setButtonStyle(RPCButtonType buttonType) {

    }
}