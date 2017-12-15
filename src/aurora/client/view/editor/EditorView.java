package aurora.client.view.editor;

import aurora.client.view.editor.components.ActionBar;
import aurora.client.view.editor.components.CodeEditor;
import aurora.client.view.editor.components.OutputField;
import aurora.client.view.editor.components.StepWindow;
import com.google.gwt.user.client.ui.Composite;

public class EditorView extends Composite {
    private CodeEditor codeEditor;
    private OutputField outputField;
    private StepWindow stepWindow;
    private ActionBar actionBar;
}
