package aurora.client.view.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import org.geomajas.codemirror.client.widget.CodeMirrorPanel;


public class Editor extends Composite {
    interface EditorUiBinder extends UiBinder<Widget, Editor> {}
    private static final EditorUiBinder ourUiBinder = GWT.create(EditorUiBinder.class);
    @UiField FlowPanel inputField;

    public Editor() {
        initWidget(ourUiBinder.createAndBindUi(this));
        setUpCodeMirror();
    }

    private void setUpCodeMirror() {
        inputField.add(new CodeMirrorPanel());
    }





}