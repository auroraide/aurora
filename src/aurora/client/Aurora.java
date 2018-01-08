package aurora.client;

import aurora.client.view.AuroraView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Aurora implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
      EventBus eventBus = GWT.create(SimpleEventBus.class);

      RootLayoutPanel.get().add(new AuroraView(eventBus));
    }


}
