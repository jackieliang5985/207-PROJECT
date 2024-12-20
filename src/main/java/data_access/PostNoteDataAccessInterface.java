package data_access;

import entity.PostNoteEntity;

import java.util.List;

public interface PostNoteDataAccessInterface {
    // Add a new post note (text or image)
    void addPostNote(PostNoteEntity postNote);

    // Get all post notes
    List<PostNoteEntity> getAllPostNotes();

    void deletePostNote(PostNoteEntity postNote);

}
