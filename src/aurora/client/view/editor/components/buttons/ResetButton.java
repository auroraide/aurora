package aurora.client.view.editor.components.buttons;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;


public class ResetButton extends ActionButton {


    interface ResetButtonUiBinder extends UiBinder<Widget, ResetButton> {
    }

    private static ResetButtonUiBinder ourUiBinder = GWT.create(ResetButtonUiBinder.class);

    public ResetButton() {

        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    void onActionButtonClicked(ClickEvent event) {

    }
}