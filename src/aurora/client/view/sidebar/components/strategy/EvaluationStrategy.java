package aurora.client.view.sidebar.components.strategy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

public class EvaluationStrategy extends Composite {
    interface EvaluationStrategyUiBinder extends UiBinder<Widget, EvaluationStrategy> {
    }

    private static EvaluationStrategyUiBinder uiBinder = GWT.create(EvaluationStrategyUiBinder.class);

    @UiField RadioButton callByValue;
    @UiField RadioButton normalOrder;
    @UiField RadioButton callByName;
    @UiField RadioButton manualSelection;

    public EvaluationStrategy() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}