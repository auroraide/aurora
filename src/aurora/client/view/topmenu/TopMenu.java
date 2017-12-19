package aurora.client.view.topmenu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;


public class TopMenu extends Composite {
    interface TopMenuUiBinder extends UiBinder<Widget, TopMenu> {}
    private static final TopMenuUiBinder uiBinder = GWT.create(TopMenuUiBinder.class);
    @UiField Hyperlink logoURL;
    @UiField Hyperlink tutoriaURL;

    public TopMenu() {
        initWidget(uiBinder.createAndBindUi(this));
    }

}
