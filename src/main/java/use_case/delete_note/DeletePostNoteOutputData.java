package use_case.delete_note;

/**
 * Output Data for the DeletePostNote Use Case.
 */
public class DeletePostNoteOutputData {
    private final boolean success;
    private final String message;

    // Constructor
    public DeletePostNoteOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
