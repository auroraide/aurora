package aurora.client.view.editor.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StepField extends Composite {
    interface StepFieldUiBinder extends UiBinder<Widget, StepField> {
    }

    private static StepFieldUiBinder ourUiBinder = GWT.create(StepFieldUiBinder.class);

    public StepField() {

        initWidget(ourUiBinder.createAndBindUi(this));
    }
}