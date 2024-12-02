package data_access;

import entity.PostNoteEntity;
import entity.TextPostNoteEntity;
import use_case.change_color.ChangeColorNoteDataAccessInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryPostNoteDataAccessObject implements PostNoteDataAccessInterface, ChangeColorNoteDataAccessInterface {
    private final List<PostNoteEntity> postNotes;

    // Constructor to initialize the list
    public InMemoryPostNoteDataAccessObject() {
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

    public List<TextPostNoteEntity> getTextPostNotes() {
        System.out.println("Fetching all TextPostNotes from DAO");
        final List<TextPostNoteEntity> textPostNotes = new ArrayList<>();
        for (PostNoteEntity postNote : postNotes) {
            // Add post-it note to list if it is a text post-it note
            if (postNote instanceof TextPostNoteEntity) {
                textPostNotes.add((TextPostNoteEntity) postNote);
            }
        }
        return textPostNotes;
    }

    @Override
    public TextPostNoteEntity changeColorOfPostNote(int x, int y, int width, int height, Color color) {
        TextPostNoteEntity targetNote = null;
        for (PostNoteEntity postNote : postNotes) {
            if (postNote.getX() == x && postNote.getY() == y
                    && postNote.getWidth() == width && postNote.getHeight() == height) {
                ((TextPostNoteEntity) postNote).setColor(color);
                targetNote = (TextPostNoteEntity) postNote;
                break;
            }
        }
        return targetNote;
    }

    @Override
    public void deletePostNote(PostNoteEntity postNote) {
        // Remove the post note from the list
        postNotes.remove(postNote);
        System.out.println("PostNote deleted from DAO: " + postNote);  // Print statement to test
    }
}
