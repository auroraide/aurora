package aurora.client;

import aurora.client.view.AuroraView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Aurora implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
      RootLayoutPanel.get().add(new AuroraView());
    }


}
