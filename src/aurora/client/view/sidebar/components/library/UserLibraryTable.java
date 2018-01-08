package aurora.client.view.sidebar.components.library;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import aurora.client.view.sidebar.components.library.LibraryItem;

public class UserLibraryTable extends LibraryTable {


    interface UserLibraryTableUiBinder extends UiBinder<Widget, UserLibraryTable> {
    }

    private static UserLibraryTableUiBinder ourUiBinder = GWT.create(UserLibraryTableUiBinder.class);
    private List<LibraryItem> libraryItems;

    public UserLibraryTable() {
        initWidget(ourUiBinder.createAndBindUi(this));
        loadData();
    }

    @Override
    void loadData() {

    }
}