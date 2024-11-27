package interface_adapter.delete_note;

import use_case.delete_note.DeletePostNoteInputBoundary;
import use_case.delete_note.DeletePostNoteInputData;
import use_case.delete_note.DeletePostNoteOutputBoundary;

public class DeletePostNoteController {
    private final DeletePostNoteInputBoundary interactor;
    private final DeletePostNoteOutputBoundary presenter;

    // Constructor
    public DeletePostNoteController(DeletePostNoteInputBoundary interactor, DeletePostNoteOutputBoundary presenter) {
        this.interactor = interactor;
        this.presenter = presenter;
    }

    // Method to handle the deletion request
    public void deletePostNote(int x, int y, int width, int height) {
        // Create the input data object for deletion
        DeletePostNoteInputData inputData = new DeletePostNoteInputData(x, y, width, height);

        // Call the interactor to delete the post note
        interactor.execute(inputData);
    }
}
