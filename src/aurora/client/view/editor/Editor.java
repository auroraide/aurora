package aurora.client.view.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Editor extends Composite {
    interface EditorUiBinder extends UiBinder<VerticalPanel, Editor> {}
    private static final EditorUiBinder uiBinder = GWT.create(EditorUiBinder.class);


}