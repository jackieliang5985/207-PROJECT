package use_case.add_connection;

public class AddConnectionInputData {
    private final String fromNoteID;
    private final String toNoteID;

    public AddConnectionInputData(String fromNoteID, String toNoteID) {
        this.fromNoteID = fromNoteID;
        this.toNoteID = toNoteID;
    }

    public String getFromNoteID() {
        return fromNoteID;
    }

    public String getToNoteID() {
        return toNoteID;
    }
}
