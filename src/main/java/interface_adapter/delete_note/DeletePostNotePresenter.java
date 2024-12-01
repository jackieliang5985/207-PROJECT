package interface_adapter.delete_note;

import use_case.delete_note.DeletePostNoteOutputBoundary;
import use_case.delete_note.DeletePostNoteOutputData;

public class DeletePostNotePresenter implements DeletePostNoteOutputBoundary {

    private final DeletePostNoteViewModel viewModel;

    public DeletePostNotePresenter(DeletePostNoteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentDeletePostNoteResult(DeletePostNoteOutputData outputData) {
        // Check if the deletion was successful
        if (outputData.isSuccess()) {
            // If successful, update the ViewModel to reflect the success state
            viewModel.onPostNoteDeleted();  // This will set isDeletedSuccessfully to true and clear the errorMessage
        } else {
            // If failure, update the ViewModel with the failure message
            viewModel.onDeleteFailed(outputData.getMessage());  // This will set isDeletedSuccessfully to false and set the errorMessage
        }
    }
}
