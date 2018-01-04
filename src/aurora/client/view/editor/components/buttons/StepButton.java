package aurora.client.view.editor.components.buttons;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class StepButton extends ActionButton {
    interface StepButtonUiBinder extends UiBinder<Widget, StepButton> {
    }

    private static StepButtonUiBinder ourUiBinder = GWT.create(StepButtonUiBinder.class);

    @UiField Label stepNumber;

    public StepButton() {

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    void onActionButtonClicked(ClickEvent event) {

    }

    public void updateStepNumber(int stepNumber) {

    }


}