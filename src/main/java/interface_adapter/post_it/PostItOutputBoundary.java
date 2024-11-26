package interface_adapter.post_it;

import entity.PostNoteEntity;
import entity.TextPostNoteEntity;

public interface PostItOutputBoundary {
    void prepareSuccessView(PostNoteEntity postNote);

    void prepareSuccessView(TextPostNoteEntity postNote);
}
