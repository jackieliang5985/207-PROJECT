package interface_adapter.delete_note;

import use_case.delete_note.DeletePostNoteOutputBoundary;
import use_case.delete_note.DeletePostNoteOutputData;


/**
 * The presenter for the DeleteNote Use Case.
 */
public class DeletePostNotePresenter implements DeletePostNoteOutputBoundary {

    private final DeletePostNoteViewModel viewModel;

    public DeletePostNotePresenter(DeletePostNoteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentDeletePostNoteResult(DeletePostNoteOutputData outputData) {
        // Check if the deletion was successful
        if (outputData.isSuccess()) {
            viewModel.onPostNoteDeleted();
        }
        else {
            viewModel.onDeleteFailed(outputData.getMessage());
        }
    }
}
