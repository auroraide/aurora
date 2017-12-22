package aurora.client.view.sidebar.components;

import com.google.gwt.user.client.ui.Composite;

public class SidebarElement extends Composite {
    private ExpandBehaviour expandBehaviour;

    public SidebarElement(ExpandBehaviour expandBehaviour) {
        this.expandBehaviour = expandBehaviour;
    }

    public void performExpand() {
        expandBehaviour.expand();
    }
    
    public void setExpandBehaviour(ExpandBehaviour expandBehaviour) {
        this.expandBehaviour = expandBehaviour;
    }
}
