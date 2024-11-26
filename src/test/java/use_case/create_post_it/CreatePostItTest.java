package use_case.create_post_it;

import entity.MindMapEntity;
import entity.PostNoteEntity;
import interface_adapter.post_it.PostItOutputBoundary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreatePostItTest {

    private MindMapEntity mindMap;
    private PostItOutputBoundary outputBoundary;
    private CreatePostIt createPostIt;

    @BeforeEach
    public void setUp() {
        mindMap = new MindMapEntity("Test Mind Map");
        outputBoundary = new PostItOutputBoundary() {
            @Override
            public void prepareSuccessView(PostNoteEntity postNote) {
                // Mock implementation to simulate view update
                assertNotNull(postNote);
                assertEquals("Test Mind Map", postNote.getMindMap().getMindMapTitle());
            }
        };
        createPostIt = new CreatePostIt(outputBoundary, mindMap);
    }

    @Test
    public void testCreatePostIt() {
        // Input data for creating a post-it note
        CreatePostItInputData inputData = new CreatePostItInputData(10, 20, 100, 50);

        // Call the execute method to create a new post-it note
        createPostIt.execute(inputData);

        // Verify that the post-it note was added to the mind map
        assertEquals(1, mindMap.getPostNotes().size());
        PostNoteEntity createdPostNote = mindMap.getPostNotes().get(0);
        assertNotNull(createdPostNote);
        assertEquals(10, createdPostNote.getX());
        assertEquals(20, createdPostNote.getY());
    }
}
