package aurora.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddFunctionEvent extends GwtEvent<AddFunctionEventHandler> {
    public static Type<AddFunctionEventHandler> TYPE = new Type<>();
    private String name;
    private String lambdaTerm;
    private String description;

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

    public String getName() {
        return name;
    }

    public String getLambdaTerm() {
        return lambdaTerm;
    }

    public String getDescription() {
        return description;
    }
}
