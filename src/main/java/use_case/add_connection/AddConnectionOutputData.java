package use_case.add_connection;

public class AddConnectionOutputData {
    private final boolean success;
    private final String message;

    public AddConnectionOutputData(boolean success, String message) {
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
