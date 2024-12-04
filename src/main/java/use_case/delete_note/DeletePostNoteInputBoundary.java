package use_case.delete_note;

/**
 * Input Boundary for actions which are related to deleting a note.
 */
public interface DeletePostNoteInputBoundary {

    /**
     * Executes the signup use case.
     * @param deletePostNoteInputData the input data
     */
    void execute(DeletePostNoteInputData deletePostNoteInputData);
}
