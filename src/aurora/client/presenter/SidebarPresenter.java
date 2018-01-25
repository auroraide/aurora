package aurora.client.presenter;

import aurora.client.AuroraDisplay;
import com.google.gwt.event.shared.EventBus;


/**
 * <code>SidebarPresenter</code> is responsible for the presentation logic.
 * <p>
 * <code>Aurora Presenter</code> then updates the view through
 * via the {@link AuroraDisplay}.
 */
public class SidebarPresenter {
    private final AuroraDisplay auroraDisplay;

    /**
     * Creates an <code>EditorPresenter</code> with an {@link EventBus} and a {@link AuroraDisplay}.
     *
     * @param auroraDisplay The {@link AuroraDisplay}
     */
    public SidebarPresenter(AuroraDisplay auroraDisplay) {
        this.auroraDisplay = auroraDisplay;
    }


}
