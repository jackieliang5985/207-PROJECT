package use_case.move_post_it;

import entity.MindMapEntity;
import entity.PostNoteEntity;
import entity.TextPostNoteEntity;
import interface_adapter.post_it.PostItOutputBoundary;
import use_case.create_post_it.CreatePostIt;
import use_case.create_post_it.CreatePostItInputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovePostItTest {

    private MindMapEntity mindMap;
    private MovePostIt movePostIt;
    private PostItOutputBoundary outputBoundary;

    @BeforeEach
    public void setUp() {
        mindMap = new MindMapEntity("Test Mind Map");
        outputBoundary = new PostItOutputBoundary() {
            @Override
            public void prepareSuccessView(PostNoteEntity postNote) {
                // Mock implementation to simulate view update
                assertNotNull(postNote);
                assertEquals(30, postNote.getX());
                assertEquals(40, postNote.getY());
            }

            @Override
            public void prepareSuccessView(TextPostNoteEntity postNote) {

            }
        };

        // Create a post-it note to move
        CreatePostItInputData inputData = new CreatePostItInputData(10, 20, 100, 50);
        CreatePostIt createPostIt = new CreatePostIt(outputBoundary, mindMap);
        createPostIt.execute(inputData);

        movePostIt = new MovePostIt(outputBoundary, mindMap);
    }

    @Test
    public void testMovePostIt() {
        PostNoteEntity postNote = mindMap.getPostNotes().get(0);

        // Move the post-it note
        MovePostItInputData moveData = new MovePostItInputData(postNote.getId(), 30, 40);
        movePostIt.executeMovePostIt(moveData);

        // Verify the new position of the post-it note
        assertEquals(30, postNote.getX());
        assertEquals(40, postNote.getY());
    }
}
