package aurora.client.view;

import aurora.client.AuroraDisplay;
import aurora.client.EditorDisplay;
import aurora.client.SidebarDisplay;
import aurora.client.view.editor.EditorView;
import aurora.client.view.popup.ShareDialogBox;
import aurora.client.view.sidebar.SidebarView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


/**
 * Knows the layout of the component tree.
 * Receives "direct" events from actionbar and emits more semantic events onto the event bus.
 * Look into the aurora.client.event package.
 * TODO this doc is incomplete.
 */
public class AuroraView extends Composite implements AuroraDisplay {

    private static DesktopViewUiBinder ourUiBinder = GWT.create(DesktopViewUiBinder.class);

    private final EventBus eventBus;

    private final ShareDialogBox latexDialogBox;

    private final ShareDialogBox shortLinkDialogBox;

    @UiField(provided = true)
    private EditorView editor;

    @UiField(provided = true)
    private SidebarView sidebar;

    private State currentState;

    /**
     * Constructor.
     *
     * @param eventBus Instance of current {@link EventBus}.
     */
    @UiConstructor
    public AuroraView(EventBus eventBus) {
        this.eventBus = eventBus;
        initWidget(ourUiBinder.createAndBindUi(this));
        latexDialogBox = new ShareDialogBox("Share LaTeX");
        shortLinkDialogBox = new ShareDialogBox("Share short link");

        currentState = new Default();

        // editor.getActionBar().onRunButtonClick(e -> {
        //     currentState = currentState.run();
        //     eventBus.fire(new RunEvent());
        // });
    }

    @Override
    public void displayLatexSnippetDialog(String latexCode) {

    }

    @Override
    public void displayShortLinkDialog(String shortLink) {

    }

    @Override
    public void setStepNumber(int stepNumber) {

    }

    private void bind() {
        // on click share latex (ist in sidebar):
        //      eventBus.fireEvent(new ShareLatexenvetdings(editor.balbalbalba))

        // on click share latex in step (ist in editor):
        //      eventBus.fireEvent(new ShareLatexenvetdings(editor.step[i].balbalbalba))

        // on step number change IN SIDEBAR:
        //      editor.stepnumber = ...;
        //      eventBus.fireevent(step chagned....)

        // on step number change IN EDITOR:
        //      sidebar.stepnumber = ...;
        //      eventBus.fireevent(step chagned....)
    }

    public EditorDisplay getEditor() {
        return editor;
    }

    public SidebarDisplay getSidebar() {
        return sidebar;
    }

    interface DesktopViewUiBinder extends UiBinder<Widget, AuroraView> {
    }

    private interface State {
        State run();

        State pause();

        State resume();

        State reset();

        State step();

        //        default State run() { throw new IllegalStateException(); }
        //        default State pause() { throw new IllegalStateException(); }
        //        default State resume() { throw new IllegalStateException(); }
        //        default State reset() { throw new IllegalStateException(); }
        //        default State step() { throw new IllegalStateException(); }
    }

    private static class Paused implements State {
        @Override
        public State run() {
            return null;
        }

        @Override
        public State pause() {
            return null;
        }

        @Override
        public State resume() {
            return null;
        }

        @Override
        public State reset() {
            return null;
        }

        @Override
        public State step() {
            return null;
        }
    }

    private class Default implements State {
        public State run() {
            return new Running();
        }

        @Override
        public State pause() {
            return null;
        }

        @Override
        public State resume() {
            return null;
        }

        @Override
        public State reset() {
            return null;
        }

        @Override
        public State step() {
            return null;
        }
    }

    private class Finished implements State {
        @Override
        public State run() {
            return null;
        }

        @Override
        public State pause() {
            return null;
        }

        @Override
        public State resume() {
            return null;
        }

        @Override
        public State reset() {
            return null;
        }

        @Override
        public State step() {
            return null;
        }
    }

    private class Running implements State {

        @Override
        public State run() {
            return null;
        }

        @Override
        public State pause() {
            return null;
        }

        @Override
        public State resume() {
            return null;
        }

        @Override
        public State reset() {
            return null;
        }

        @Override
        public State step() {
            return null;
        }
    }
}
