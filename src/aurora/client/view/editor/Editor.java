package aurora.client.view.editor;

import aurora.client.view.editor.components.ActionBar;
import aurora.client.view.editor.components.OutputField;
import aurora.client.view.editor.components.StepField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Editor extends Composite {
    interface EditorUiBinder extends UiBinder<VerticalPanel, Editor> {}
    private static final EditorUiBinder uiBinder = GWT.create(EditorUiBinder.class);


}