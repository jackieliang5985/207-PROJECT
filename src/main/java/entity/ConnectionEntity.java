
package entity;

public class ConnectionEntity {
    private final String id;
    private final String fromNoteId;
    private final String toNoteId;

    public ConnectionEntity(String id, String fromNoteId, String toNoteId) {
        this.id = id;
        this.fromNoteId = fromNoteId;
        this.toNoteId = toNoteId;
    }

    public String getId() {
        return id;
    }

    public String getFromNoteId() {
        return fromNoteId;
    }

    public String getToNoteId() {
        return toNoteId;
    }
}

