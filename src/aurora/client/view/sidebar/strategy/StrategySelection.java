package aurora.client.view.sidebar.strategy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

/**
 * Provides Buttons for all available stretegies.
 */
public class StrategySelection extends Composite {
    private static StrategySelectionUiBinder ourUiBinder = GWT.create(StrategySelectionUiBinder.class);
    @UiField
    RadioButton callByValue;
    @UiField
    RadioButton callByName;
    @UiField
    RadioButton normalOrder;
    @UiField
    RadioButton manualSelection;

    /**
     * Creates the strategy selection area.
     */
    public StrategySelection() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /**
     * Getter for CallByValue.
     *
     * @return callByValue
     */
    public RadioButton getCallByValue() {
        return callByValue;
    }

    /**
     * Getter for CallByName.
     *
     * @return callByName
     */
    public RadioButton getCallByName() {
        return callByName;
    }

    /**
     * Getter for normal order.
     *
     * @return normalOrder
     */
    public RadioButton getNormalOrder() {
        return normalOrder;
    }

    /**
     * Getter for manual selection.
     *
     * @return manualSelection
     */
    public RadioButton getManualSelection() {
        return manualSelection;
    }

    /**
     * Enables or disables the StrategySelection widget.
     *
     * @param enabled set true to enable or false to disable.
     */
    public void setEnabled(boolean enabled) {
        this.normalOrder.setEnabled(enabled);
        this.callByName.setEnabled(enabled);
        this.callByValue.setEnabled(enabled);
        this.manualSelection.setEnabled(enabled);
    }

    interface StrategySelectionUiBinder extends UiBinder<Widget, StrategySelection> {
    }
}
