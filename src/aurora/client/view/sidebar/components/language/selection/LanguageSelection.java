package aurora.client.view.sidebar.components.language.selection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class LanguageSelection extends Composite {
    interface LanguageSelectionUiBinder extends UiBinder<Widget, LanguageSelection> { }

    private static LanguageSelectionUiBinder uiBinder = GWT.create(LanguageSelectionUiBinder.class);



    public LanguageSelection() {
        initWidget(uiBinder.createAndBindUi(this));

    }

}