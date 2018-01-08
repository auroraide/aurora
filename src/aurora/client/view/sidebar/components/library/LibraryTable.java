package aurora.client.view.sidebar.components.library;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

public abstract class LibraryTable extends Composite {
    @UiField private FlexTable table;


    abstract void loadData();
}
