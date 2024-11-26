package use_case.edit_post_it;

import entity.MindMapEntity;
import entity.PostNoteEntity;
import entity.TextPostNoteEntity;
import interface_adapter.post_it.PostItOutputBoundary;
import use_case.create_post_it.CreatePostIt;
import use_case.create_post_it.CreatePostItInputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EditPostItTest {

    private MindMapEntity mindMap;
    private EditPostIt editPostIt;
    private PostItOutputBoundary outputBoundary;

    @BeforeEach
    public void setUp() {
        mindMap = new MindMapEntity("Test Mind Map");
        outputBoundary = new PostItOutputBoundary() {
            @Override
            public void prepareSuccessView(PostNoteEntity postNote) {

            }

            @Override
            public void prepareSuccessView(TextPostNoteEntity postNote) {
                // Mock implementation to simulate view update
                assertNotNull(postNote);
                assertEquals("Updated Text", postNote.getText());
            }
        };

        // Create a post-it note to edit
        CreatePostItInputData inputData = new CreatePostItInputData(10, 20, 100, 50);
        CreatePostIt createPostIt = new CreatePostIt(outputBoundary, mindMap);
        createPostIt.execute(inputData);

        editPostIt = new EditPostIt(outputBoundary, mindMap);
    }

    @Test
    public void testEditPostIt() {
        TextPostNoteEntity postNote = (TextPostNoteEntity) mindMap.getPostNotes().get(0);

        // Edit the text of the post-it note
        EditPostItInputData editData = new EditPostItInputData(postNote.getId(), "Updated Text");
        editPostIt.executeEditPostIt(editData);

        // Verify that the post-it note's text was updated
        assertEquals("Updated Text", postNote.getText());
    }
}
