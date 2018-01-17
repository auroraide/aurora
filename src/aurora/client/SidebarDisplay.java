package aurora.client;

/**
 * The implementation of {@link aurora.client.view.sidebar.SidebarView} is hidden behind the
 * <code>SidebarDisplay</code> interface.
 *
 * <p><code>SidebarDisplay</code> defines the interface, that the {@link aurora.client.presenter.SidebarPresenter}
 * uses to communicate with the view.
 * A view implementing this interface, therefore provides necessary methods for the above mentioned
 * communication purposes regarding the presenter.
 */
public interface SidebarDisplay {

    /**
     * Closes an add function dialog form.
     */
    void closeAddLibraryItemDialog();

    /**
     * Adds a new function to the views user library.
     *
     * @param name        The name function.
     * @param description The function description.
     */
    void addUserLibraryItem(String name, String description);

    /**
     * Removes a function from the view's user library.
     *
     * @param name The name of the function to be removed.
     */
    void removeUserLibraryItem(String name);


    /**
     * Adds a new function to the views' standard library.
     *
     * @param name        The name of the function to be added.
     * @param description The function description.
     */
    void addStandardLibraryItem(String name, String description);

    /**
     * Removes a function form the views' standard library.
     *
     * @param name The name of the function to be removed.
     */
    void removeStandardLibraryItem(String name);

}
