package use_case.move_post_it;

public class MovePostItInputData {
    private final String postNoteId;
    private final int newX;
    private final int newY;

    public MovePostItInputData(String postNoteId, int newX, int newY) {
        this.postNoteId = postNoteId;
        this.newX = newX;
        this.newY = newY;
    }

    public String getPostNoteId() { return postNoteId; }
    public int getNewX() { return newX; }
    public int getNewY() { return newY; }
}
