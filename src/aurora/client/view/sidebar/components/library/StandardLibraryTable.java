package aurora.client.view.sidebar.components.library;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import aurora.client.view.sidebar.components.library.LibraryItem;

public class StandardLibraryTable extends LibraryTable {
    interface StandardLibraryTableUiBinder extends UiBinder<Widget, StandardLibraryTable> {
    }

    private static StandardLibraryTableUiBinder ourUiBinder = GWT.create(StandardLibraryTableUiBinder.class);
    private List<LibraryItem> libraryItems;


    public StandardLibraryTable() {
        initWidget(ourUiBinder.createAndBindUi(this));
        loadData();
    }

    @Override
    void loadData() {

    }
}