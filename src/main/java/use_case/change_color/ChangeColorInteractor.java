package use_case.change_color;

import data_access.PostNoteDAO;
import entity.MindMapEntity;
import entity.PostNoteEntity;
import entity.TextPostNoteEntity;

import java.util.List;

public class ChangeColorInteractor implements ChangeColorInputBoundary {
//    private final ChangeColorNoteDataAccessInterface noteDataAccessObject;
    private final ChangeColorOutputBoundary notePresenter;
    private final PostNoteDAO postNoteDAO;

    public ChangeColorInteractor(ChangeColorOutputBoundary changeColorOutputBoundary, PostNoteDAO postNoteDAO) {
//        this.noteDataAccessObject = changeColorNoteDataAccessInterface;
        this.notePresenter = changeColorOutputBoundary;
        this.postNoteDAO = postNoteDAO;
    }

    @Override
    public void execute(ChangeColorInputData changeColorInputData) {
        // Get all the post notes
        final List<PostNoteEntity> postNotes = postNoteDAO.getAllPostNotes();
        // Try to find the post note to change the color of
        boolean colorChanged = false;
        for (PostNoteEntity postNote : postNotes) {
            if (postNote.getX() == changeColorInputData.getX() &&
                    postNote.getY() == changeColorInputData.getY() &&
                    postNote.getWidth() == changeColorInputData.getWidth() &&
                    postNote.getHeight() == changeColorInputData.getHeight()) {
                // Change the color of the post note in DAO
                ((TextPostNoteEntity) postNote).setColor(changeColorInputData.getColor());
                colorChanged = true;
                final ChangeColorOutputData changeColorOutputData = new ChangeColorOutputData(
                        ((TextPostNoteEntity) postNote).getText(), postNote.getX(), postNote.getY(),
                        postNote.getWidth(), postNote.getHeight(), ((TextPostNoteEntity) postNote).getColor());
                changeColorPresenter.prepareSuccessView(changeColorOutputData);
                break;
            }
        }

        if (!colorChanged) {
            changeColorPresenter.prepareFailView("Post-it note not found or color could not be changed.");
        }
    }
}
