package aurora.client.view.editor.components;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;

public class ActionBar extends Composite {
    @UiField Button runButton;
    @UiField Button stepButton;
    @UiField Button continueButton;
    @UiField Button pauseButton;
    // buttons gleich bei änderung run -> pause ->continue
}
