package entity;

import java.util.ArrayList;

/**
 * A simple implementation of the mind map entity.
 */
public class MindMapEntity {
    private String mindMapTitle;
    private ArrayList<PostNoteEntity> postNotes;

    public MindMapEntity(String mindMapTitle, ArrayList<PostNoteEntity> postNotes) {
        this.mindMapTitle = mindMapTitle;
        this.postNotes = postNotes;
    }

    public String getMindMapTitle() {
        return mindMapTitle;
    }

    public ArrayList<PostNoteEntity> getPostNotes() {
        return postNotes;
    }
}
