package interface_adapter.post_it;

import interface_adapter.create_MindMap.SquarePanel;
import entity.PostNoteEntity;

public class PostItPresenter implements PostItOutputBoundary {

    private final SquarePanel squarePanel;

    public PostItPresenter(SquarePanel squarePanel) {
        this.squarePanel = squarePanel;
    }

    @Override
    public void prepareSuccessView(PostNoteEntity postNote) {
        // Update the square panel (view) to reflect changes
        squarePanel.repaint();
    }
}
