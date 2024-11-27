package use_case.delete_note;

public class DeletePostNoteOutputData {
    private final boolean success;
    private final String message; // Message to convey success or failure details

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