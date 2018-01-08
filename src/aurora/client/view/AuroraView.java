package aurora.client.view;

import aurora.client.presenter.DesktopPresenter;
import aurora.client.view.editor.Editor;
import aurora.client.view.sidebar.Sidebar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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
    @UiField Editor editor;
    @UiField Sidebar sidebar;


    public AuroraView() {
        initWidget(ourUiBinder.createAndBindUi(this));

	/*	String initialContent = "BANANA";

		final PopupPanel popup = new PopupPanel(false);
		popup.setSize("800px", "450px");
		popup.setWidget(new CodeMirrorPanel(initialContent));
        popup.center();*/

    }
}
