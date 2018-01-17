package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Occurs when the user wants to add a function. Contains the necessary information for doing so.
 * Can be not sanitized.
 */
public class AddFunctionEvent extends GwtEvent<AddFunctionEventHandler> {
    public static Type<AddFunctionEventHandler> TYPE = new Type<>();
    private final String name;
    private final String lambdaTerm;
    private final String description;

    /**
     * Creates a new event with exactly those values. A simple data structure.
     *
     * @param name        Name of the function.
     * @param lambdaTerm  String that is supposed to represent the lambda term.
     * @param description User-provided description. Only for the user to understand.
     */
    public AddFunctionEvent(String name, String lambdaTerm, String description) {
        this.name = name;
        this.lambdaTerm = lambdaTerm;
        this.description = description;
    }


    @Override
    public Type<AddFunctionEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddFunctionEventHandler addFunctionEventHandler) {

    }

    /**
     * Gets the name of the function, as entered in the text box.
     *
     * @return Name of the function.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the supposed lambda term as entered by the user. It may still contain syntax errors.
     *
     * @return Lambda term.
     */
    public String getLambdaTerm() {
        return lambdaTerm;
    }

    /**
     * Gets the user-provided description. Only for the user to understand.
     *
     * @return user-provided description.
     */
    public String getDescription() {
        return description;
    }
}
