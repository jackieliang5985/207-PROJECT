package use_case.delete_note;
import data_access.PostNoteDataAccessInterface;
import entity.MindMapEntity;
import entity.PostNoteEntity;

import java.util.List;

public class DeletePostNoteInteractor implements DeletePostNoteInputBoundary {
    private final DeletePostNoteOutputBoundary outputBoundary;
    private final PostNoteDataAccessInterface postNoteDAO;
    private final MindMapEntity mindMapEntity;

    // Constructor
    public DeletePostNoteInteractor(DeletePostNoteOutputBoundary outputBoundary, PostNoteDataAccessInterface postNoteDAO, MindMapEntity mindMapEntity) {
        this.outputBoundary = outputBoundary;
        this.postNoteDAO = postNoteDAO;
        this.mindMapEntity = mindMapEntity;
    }

    @Override
    public void execute(DeletePostNoteInputData inputData) {
        // Get all the post notes
        List<PostNoteEntity> postNotes = postNoteDAO.getAllPostNotes();

        // Try to find the post note to delete
        boolean deleted = false;
        for (PostNoteEntity postNote : postNotes) {
            if (postNote.getX() == inputData.getX() &&
                    postNote.getY() == inputData.getY() &&
                    postNote.getWidth() == inputData.getWidth() &&
                    postNote.getHeight() == inputData.getHeight()) {
                // Delete the post note from DAO
                postNoteDAO.deletePostNote(postNote);
                deleted = true;
                break;
            }
        }

        // Prepare the result
        if (deleted) {
            outputBoundary.presentDeletePostNoteResult(new DeletePostNoteOutputData(true, "Post Note deleted successfully."));
        } else {
            outputBoundary.presentDeletePostNoteResult(new DeletePostNoteOutputData(false, "Post Note not found or could not be deleted."));
        }
    }
}