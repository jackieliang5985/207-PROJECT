package interface_adapter.delete_note;

import use_case.delete_note.DeletePostNoteInputBoundary;
import use_case.delete_note.DeletePostNoteInputData;
import use_case.delete_note.DeletePostNoteOutputBoundary;


/**
 * The controller for the DeleteNote Use Case.
 */
public class DeletePostNoteController {
    private final DeletePostNoteInputBoundary interactor;

    // Constructor
    public DeletePostNoteController(DeletePostNoteInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void deletePostNote(int x_coord, int y_coord, int width, int height) {
        // Create the input data object for deletion
        final DeletePostNoteInputData inputData = new DeletePostNoteInputData(x_coord, y_coord, width, height);

        // Call the interactor to delete the post note
        interactor.execute(inputData);
    }
}
