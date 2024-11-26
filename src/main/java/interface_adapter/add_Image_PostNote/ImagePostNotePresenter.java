package interface_adapter.add_Image_PostNote;

import entity.ImagePostNote;
import interface_adapter.create_MindMap.SquarePanel;
import use_case.add_Image_PostNote.ImagePostNoteOutputBoundary;

public class ImagePostNotePresenter implements ImagePostNoteOutputBoundary {

    private final SquarePanel boardPanel;

    public ImagePostNotePresenter(SquarePanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    @Override
    public void presentImagePostNote(ImagePostNote postNote) {
        // Add the ImagePostNote to the SquarePanel
        boardPanel.createPostNote(postNote);

        // Repaint the panel to reflect the changes
        boardPanel.revalidate();
        boardPanel.repaint();
    }
}
