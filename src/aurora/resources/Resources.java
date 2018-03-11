package aurora.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface Resources extends ClientBundle {
    public static final Resources INSTANCE =  GWT.create(Resources.class);

    @Source("StdlibFunctionData.json")
    public TextResource stdlibFunctionData();


}
