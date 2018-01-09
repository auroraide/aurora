package aurora.client.presenter;

import aurora.client.view.editor.EditorView;
import com.google.gwt.event.shared.EventBus;

public class EditorPresenter {
    private final EventBus eventBus;
    private final EditorView editorView;

    public EditorPresenter(EventBus eventBus, EditorView editorView) {
        this.eventBus = eventBus;
        this.editorView = editorView;
    }
}
