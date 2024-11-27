package data_access;

import entity.PostNoteEntity;

import java.util.List;

public interface PostNoteDAO {
    // Add a new post note (text or image)
    void addPostNote(PostNoteEntity postNote);

    // Get all post notes
    List<PostNoteEntity> getAllPostNotes();
}
