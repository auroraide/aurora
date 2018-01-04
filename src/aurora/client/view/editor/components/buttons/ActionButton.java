package aurora.client.view.editor.components.buttons;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;

public abstract class ActionButton extends Composite {
    @UiField Button actionButton;

    public void enable() {
        actionButton.setEnabled(true);
    }

    public void disable() {
        actionButton.setEnabled(false);
    }

    @UiHandler("actionButton")
    abstract void onActionButtonClicked(ClickEvent event);


}
