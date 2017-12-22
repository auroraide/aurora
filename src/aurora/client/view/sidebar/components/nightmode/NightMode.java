package aurora.client.view.sidebar.components.nightmode;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

public class NightMode extends Composite {
    interface NightModeUiBinder extends UiBinder<Widget, NightMode> {}

    private static NightModeUiBinder uiBinder = GWT.create(NightModeUiBinder.class);

    @UiField ToggleButton nightModeSwitch;

    public NightMode() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}