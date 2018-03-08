package aurora.client;

import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;

/**
 * The implementation of {@link aurora.client.view.sidebar.SidebarView} is hidden behind the
 * <code>SidebarDisplay</code> interface.
 * <p>
 * <code>SidebarDisplay</code> defines the interface, that the {@link aurora.client.presenter.SidebarPresenter}
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
     * The user made a simple syntax error in their input.
     * @param error Exception containing detailed information abotu what exactly they did wrong.
     */
    void displayAddLibraryItemSyntaxError(SyntaxException error);

    /**
     * The user made a semantic mistake in their input.
     * @param error Exception containing detailed information abotu what exactly they did wrong.
     */
    void displayAddLibraryItemSemanticError(SemanticException error);

    /**
     * User tried to add a function whose name is already taken.
     */
    void displayAddLibraryItemNameAlreadyTaken();

    /**
     * Called when the user has entered not a alphabetical name for the function to be added.
     */
    void displayAddLibraryItemInvalidName();

    /**
     * Adds a new function to the views user library.
     *
     * @param name        The name function.
     * @param description The function description.
     */
    void addUserLibraryItem(String name, String description);

    /**
     * Adds a new function to the views' standard library.
     *
     * @param name        The name of the function to be added.
     * @param description The function description.
     */
    void addStandardLibraryItem(String name, String description);
}
