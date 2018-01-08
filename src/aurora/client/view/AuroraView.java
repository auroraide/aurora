package aurora.client.view;

import aurora.client.presenter.DesktopPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import org.geomajas.codemirror.client.widget.CodeMirrorPanel;

import aurora.client.view.popup.InfoDialogBox;

//REMOVE ONCE CODEMIRROR IS NO LONGER IN PANEL
import com.google.gwt.user.client.ui.PopupPanel;

public class AuroraView extends Composite implements DesktopPresenter.Display {
    interface DesktopViewUiBinder extends UiBinder<Widget, AuroraView> {
    }

    private static DesktopViewUiBinder ourUiBinder = GWT.create(DesktopViewUiBinder.class);

    public AuroraView() {
        initWidget(ourUiBinder.createAndBindUi(this));

		String initialContent = "BANANA";

		final PopupPanel popup = new PopupPanel(false);
		popup.setSize("800px", "450px");
		popup.setWidget(new CodeMirrorPanel(initialContent));
        popup.center();

    }
}
