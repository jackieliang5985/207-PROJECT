package use_case.delete_note;

import java.util.List;

import data_access.PostNoteDataAccessInterface;
import entity.MindMapEntity;
import entity.PostNoteEntity;

/**
 * The Interactor for the Delete Note Use Case.
 */
public class DeletePostNoteInteractor implements DeletePostNoteInputBoundary {
    private final DeletePostNoteOutputBoundary outputBoundary;
    private final PostNoteDataAccessInterface postNoteDataAccessInterface;
    private final MindMapEntity mindMapEntity;

    public DeletePostNoteInteractor(DeletePostNoteOutputBoundary outputBoundary,
                                    PostNoteDataAccessInterface postNoteDataAccessInterface,
                                    MindMapEntity mindMapEntity) {
        this.outputBoundary = outputBoundary;
        this.postNoteDataAccessInterface = postNoteDataAccessInterface;
        this.mindMapEntity = mindMapEntity;
    }

    @Override
    public void execute(DeletePostNoteInputData inputData) {

        final List<PostNoteEntity> postNotes = postNoteDataAccessInterface.getAllPostNotes();

        boolean deleted = false;
        for (PostNoteEntity postNote : postNotes) {
            if (postNote.getX() == inputData.getX()
                    &&
                    postNote.getY() == inputData.getY()
                    &&
                    postNote.getWidth() == inputData.getWidth()
                    &&
                    postNote.getHeight() == inputData.getHeight()) {

                postNoteDataAccessInterface.deletePostNote(postNote);
                deleted = true;
                break;
            }
        }

        if (deleted) {
            outputBoundary.presentDeletePostNoteResult(
                    new DeletePostNoteOutputData(true, "Post Note deleted successfully."));
        }
        else {
            outputBoundary.presentDeletePostNoteResult(
                    new DeletePostNoteOutputData(false, "Post Note not found or could not be deleted."));
        }
    }
}
