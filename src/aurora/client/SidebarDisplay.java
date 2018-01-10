package aurora.client;

public interface SidebarDisplay {

    /**
     * Closes an add function dialog form.
     */
    void closeAddLibraryItemDialog();

    /**
     * Adds a new function to the views userlibrary.
     * @param name The name function.
     * @param description The function description.
     */
    void addUserLibraryItem(String name, String description);

    /**
     * Removes a function from the view's user library
     * @param name The name of the function to be removed.
     */
    void removeUserLibraryItem(String name);

    void addStandardLibraryItem(String name, String description);

    void removeStandardLibraryItem(String name);
}
