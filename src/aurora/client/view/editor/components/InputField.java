package aurora.client.view.editor.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class InputField extends Composite {
    interface InputFieldUiBinder extends UiBinder<Widget, InputField> {
    }

    private static InputFieldUiBinder ourUiBinder = GWT.create(InputFieldUiBinder.class);

    public InputField() {

        initWidget(ourUiBinder.createAndBindUi(this));
    }
}