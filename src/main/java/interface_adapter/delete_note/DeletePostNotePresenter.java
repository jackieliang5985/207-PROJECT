package interface_adapter.delete_note;

import use_case.delete_note.DeletePostNoteOutputBoundary;
import use_case.delete_note.DeletePostNoteOutputData;

public class DeletePostNotePresenter implements DeletePostNoteOutputBoundary {
    @Override
    public void presentDeletePostNoteResult(DeletePostNoteOutputData outputData) {
        // Here you can format the message however you want, for now let's just print it
        if (outputData.isSuccess()) {
            System.out.println("Success: " + outputData.getMessage());
        } else {
            System.out.println("Failure: " + outputData.getMessage());
        }
    }
}
