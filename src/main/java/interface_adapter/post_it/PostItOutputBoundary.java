package interface_adapter.post_it;

import entity.PostNoteEntity;

public interface PostItOutputBoundary {
    void prepareSuccessView(PostNoteEntity postNote);
}
