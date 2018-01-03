package aurora.client.view.sidebar.components.strategy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

public class StrategySelection extends Composite {
    interface StrategySelectionUiBinder extends UiBinder<Widget, StrategySelection> {
    }

    private static StrategySelectionUiBinder ourUiBinder = GWT.create(StrategySelectionUiBinder.class);

    @UiField RadioButton callByValue;
    @UiField RadioButton callByName;
    @UiField RadioButton normalOrder;
    @UiField RadioButton manualSelection;

    public StrategySelection() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @UiHandler("callByValue")
    void onCallByValueSelected(ClickEvent event) {

    }

    @UiHandler("callByName")
    void onCallByNameSelected(ClickEvent event) {

    }

    @UiHandler("normalOrder")
    void onNormalOrderSelected(ClickEvent event) {

    }

    @UiHandler("manualSelection")
    void onManualSelectionSelected(ClickEvent event) {

    }
}