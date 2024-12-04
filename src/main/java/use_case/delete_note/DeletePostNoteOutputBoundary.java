package use_case.delete_note;

/**
 * The output boundary for the DeletePostNote Use Case.
 */
public interface DeletePostNoteOutputBoundary {

    /**
     * Prepares the success or failure view for the Login Use Case.
     * @param outputData the output data
     */
    void presentDeletePostNoteResult(DeletePostNoteOutputData outputData);
}
