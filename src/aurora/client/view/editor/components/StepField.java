package aurora.client.view.editor.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class StepField extends Composite {
    interface StepFieldUiBinder extends UiBinder<Widget, StepField> {
    }

    @UiField FlexTable stepsTable;

    private static StepFieldUiBinder ourUiBinder = GWT.create(StepFieldUiBinder.class);
    @UiField private ListBox termLineSettings;

    public StepField() {
        initWidget(ourUiBinder.createAndBindUi(this));
        setUpStepsTable();
    }

    public void addLambdaTerm() {}

    public void clearStepsTable() {
        stepsTable.clear();
    }

    private void setUpStepsTable() {
        //supposed to add List of Terms as an attribute
        /* cp (column position)
            0: Line number
            1: Settings Widget which appears on mouse hover
            2: Lambda term
         */
    }






}