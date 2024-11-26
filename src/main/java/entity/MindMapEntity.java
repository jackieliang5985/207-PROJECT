package entity;

import java.util.List;
import java.util.ArrayList;

public class MindMapEntity {
    private String mindMapTitle;
    private List<PostNoteEntity> postNotes;  // List to store post-it notes

    public MindMapEntity(String mindMapTitle) {
        this.mindMapTitle = mindMapTitle;
        this.postNotes = new ArrayList<>();
    }

    public String getMindMapTitle() {
        return mindMapTitle;
    }

    public List<PostNoteEntity> getPostNotes() {
        return postNotes;
    }

    public void addPostNote(PostNoteEntity postNote) {
        this.postNotes.add(postNote);
    }

    // Method to retrieve a post-it note by its unique ID
    public PostNoteEntity getPostNoteById(String postNoteId) {
        for (PostNoteEntity postNote : postNotes) {
            if (postNote.getId().equals(postNoteId)) {  // Now `getId()` works
                return postNote;
            }
        }
        return null;  // Return null if the post-it note is not found
    }
}
