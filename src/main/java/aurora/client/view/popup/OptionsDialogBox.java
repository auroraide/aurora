package aurora.client.view.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

public class OptionsDialogBox extends DialogBox {
    private static OptionsDialogBoxUiBinder uiBinder = GWT.create(OptionsDialogBoxUiBinder.class);
    @UiField
    Button latexExportButton;
    @UiField
    Button openTabButton;
    @UiField
    Button toPastebinButton;

    /**
     * Build a new OptionsDialogBox.
     * A small popup containing different kinds of options on what to do with the corresponding input.
     */
    public OptionsDialogBox() {
        setWidget(uiBinder.createAndBindUi(this));
        setAutoHideEnabled(true);
        setText("TITLETEXT");
        setGlassEnabled(true);
        center();
        hide();
    }

    /**
     * Getter for latexExportButton.
     *
     * @return return latexExportButton
     */
    public Button getLatexExportButton() {
        return latexExportButton;
    }

    /**
     * Getter for openTabButton.
     *
     * @return return openTabButton
     */
    public Button getOpenTabButton() {
        return openTabButton;
    }

    /**
     * Getter for toPastebinButton.
     *
     * @return toPastebinButton
     */
    public Button getToPastebinButton() {
        return toPastebinButton;
    }

    interface OptionsDialogBoxUiBinder extends UiBinder<Widget, OptionsDialogBox> {
    }


}
