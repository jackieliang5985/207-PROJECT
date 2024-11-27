package data_access;

import entity.PostNoteEntity;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPostNoteDAO implements PostNoteDAO {
    private final List<PostNoteEntity> postNotes;

    // Constructor to initialize the list
    public InMemoryPostNoteDAO() {
        this.postNotes = new ArrayList<>();
    }

    @Override
    public void addPostNote(PostNoteEntity postNote) {
        System.out.println("Adding PostNote to DAO: " + postNote);  // Print statement to test
        postNotes.add(postNote);  // Add the post note to the list
        System.out.println("this is the x" + postNote.getX());
        System.out.println("this is the y:" + postNote.getY());
    }

    @Override
    public List<PostNoteEntity> getAllPostNotes() {
        System.out.println("Fetching all PostNotes from DAO");  // Print statement to test
        return new ArrayList<>(postNotes);  // Return a copy of the list
    }

    @Override
    public void deletePostNote(PostNoteEntity postNote) {
        // Remove the post note from the list
        postNotes.remove(postNote);
        System.out.println("PostNote deleted from DAO: " + postNote);  // Print statement to test
    }
}
