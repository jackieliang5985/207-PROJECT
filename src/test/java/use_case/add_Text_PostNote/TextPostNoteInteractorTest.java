package use_case.add_Text_PostNote;

import data_access.InMemoryPostNoteDataAccessObject;
import entity.MindMapEntity;
import entity.TextPostNoteEntity;
import entity.PostNoteEntity;
import interface_adapter.add_Text_PostNote.TextPostNoteController;
import interface_adapter.add_Text_PostNote.TextPostNotePresenter;
import interface_adapter.add_Text_PostNote.TextPostNoteViewModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

class TextPostNoteInteractorTest {

    @Test
    void testAddTextPostNote() {
        // Create the in-memory DAO and presenter
        InMemoryPostNoteDataAccessObject postNoteDAO = new InMemoryPostNoteDataAccessObject();
        TextPostNoteViewModel viewModel = new TextPostNoteViewModel();
        TextPostNotePresenter presenter = new TextPostNotePresenter(viewModel);

        // Create a MindMapEntity (assuming title and empty postNotes list)
        MindMapEntity mindMapEntity = new MindMapEntity("Test Mind Map", new ArrayList<>());

        // Create the interactor instance
        TextPostNoteInteractor interactor = new TextPostNoteInteractor(presenter, postNoteDAO, mindMapEntity);

        // Create the input data (text, coordinates, dimensions, and color)
        String text = "This is a test text post-it note";
        int x = 50;
        int y = 50;
        int width = 400;
        int height = 300;
        Color color = Color.ORANGE;

        // Create the input data object
        TextPostNoteInputData inputData = new TextPostNoteInputData(text, x, y, width, height, color);

        // Add the text post note using the interactor
        interactor.addTextPostNote(inputData);

        // Get all post notes from the DAO
        List<PostNoteEntity> allPostNotes = postNoteDAO.getAllPostNotes();

        // Assert that the post note has been added to the DAO
        assertEquals(1, allPostNotes.size(), "Expected 1 post note in the DAO.");

        // Cast the PostNoteEntity to TextPostNoteEntity and check text
        PostNoteEntity addedPostNote = allPostNotes.get(0);
        assertTrue(addedPostNote instanceof TextPostNoteEntity, "Expected post note to be an instance of TextPostNoteEntity.");

        TextPostNoteEntity textPostNote = (TextPostNoteEntity) addedPostNote;

        // Assert that the added post note matches the expected data
        assertEquals(text, textPostNote.getText(), "Text should match.");
        assertEquals(x, textPostNote.getX(), "X coordinate should match.");
        assertEquals(y, textPostNote.getY(), "Y coordinate should match.");
        assertEquals(width, textPostNote.getWidth(), "Width should match.");
        assertEquals(height, textPostNote.getHeight(), "Height should match.");

        // Assert that the viewModel is updated correctly
        assertEquals(text, viewModel.getText(), "View model text should match.");
        assertEquals(x, viewModel.getX(), "View model X coordinate should match.");
        assertEquals(y, viewModel.getY(), "View model Y coordinate should match.");
        assertEquals(color, viewModel.getColor(), "View model color should match.");
    }

    @Test
    void testAddTextPostNoteWithNullText() {
        // Arrange
        String text = null;  // Simulate a null text
        int x = 10, y = 20, width = 300, height = 200;
        Color color = Color.RED;

        InMemoryPostNoteDataAccessObject postNoteDAO = new InMemoryPostNoteDataAccessObject(); // Ensure DAO is created here
        TextPostNoteViewModel viewModel = new TextPostNoteViewModel();
        TextPostNotePresenter presenter = new TextPostNotePresenter(viewModel);

        TextPostNoteController controller = new TextPostNoteController(
                new TextPostNoteInteractor(presenter, postNoteDAO, new MindMapEntity("Test Mind Map", new ArrayList<>())),
                viewModel
        );

        // Act
        controller.addTextPostNote(text, x, y, width, height, color);

        // Assert: Make sure the post note is added with a null text
        PostNoteEntity addedPostNote = postNoteDAO.getAllPostNotes().get(0);
        assertTrue(addedPostNote instanceof TextPostNoteEntity, "Expected post note to be an instance of TextPostNoteEntity.");
        TextPostNoteEntity textPostNote = (TextPostNoteEntity) addedPostNote;
        assertNull(textPostNote.getText(), "Text should be null.");
    }

    @Test
    void testAddTextPostNoteWithEmptyText() {
        // Arrange
        String text = "";  // Simulate an empty text
        int x = 10, y = 20, width = 300, height = 200;
        Color color = Color.RED;

        InMemoryPostNoteDataAccessObject postNoteDAO = new InMemoryPostNoteDataAccessObject(); // Ensure DAO is created here
        TextPostNoteViewModel viewModel = new TextPostNoteViewModel();
        TextPostNotePresenter presenter = new TextPostNotePresenter(viewModel);

        TextPostNoteController controller = new TextPostNoteController(
                new TextPostNoteInteractor(presenter, postNoteDAO, new MindMapEntity("Test Mind Map", new ArrayList<>())),
                viewModel
        );

        // Act
        controller.addTextPostNote(text, x, y, width, height, color);

        // Assert: Make sure the post note is added with an empty text
        PostNoteEntity addedPostNote = postNoteDAO.getAllPostNotes().get(0);
        assertTrue(addedPostNote instanceof TextPostNoteEntity, "Expected post note to be an instance of TextPostNoteEntity.");
        TextPostNoteEntity textPostNote = (TextPostNoteEntity) addedPostNote;
        assertEquals("", textPostNote.getText(), "Text should be an empty string.");
    }

    @Test
    void testAddTextPostNoteWhenDAOIsEmpty() {
        // Arrange
        String text = "This is a test text post-it note";
        int x = 50, y = 100, width = 250, height = 150;
        Color color = Color.YELLOW;

        // Create an in-memory DAO and presenter
        InMemoryPostNoteDataAccessObject postNoteDAO = new InMemoryPostNoteDataAccessObject(); // Instantiate DAO
        TextPostNoteViewModel viewModel = new TextPostNoteViewModel();
        TextPostNotePresenter presenter = new TextPostNotePresenter(viewModel);

        // Create a MindMapEntity (assuming title and empty postNotes list)
        MindMapEntity mindMapEntity = new MindMapEntity("Test Mind Map", new ArrayList<>());

        // Create the interactor instance
        TextPostNoteInteractor interactor = new TextPostNoteInteractor(presenter, postNoteDAO, mindMapEntity);

        // Create the controller and bind to the interactor
        TextPostNoteController controller = new TextPostNoteController(interactor, viewModel);

        // Act: Add the text post note using the controller
        controller.addTextPostNote(text, x, y, width, height, color);

        // Assert: Make sure the post note is added to the empty DAO
        assertEquals(1, postNoteDAO.getAllPostNotes().size(), "Post note should be added to the DAO.");

        // Get the added post note
        PostNoteEntity addedPostNote = postNoteDAO.getAllPostNotes().get(0);

        // Assert: Check if it's an instance of TextPostNoteEntity
        assertTrue(addedPostNote instanceof TextPostNoteEntity, "Expected post note to be an instance of TextPostNoteEntity.");

        TextPostNoteEntity textPostNote = (TextPostNoteEntity) addedPostNote;

        // Assert: Ensure the values match
        assertEquals(text, textPostNote.getText(), "Text should match.");
        assertEquals(x, textPostNote.getX(), "X position should match.");
        assertEquals(y, textPostNote.getY(), "Y position should match.");
        assertEquals(width, textPostNote.getWidth(), "Width should match.");
        assertEquals(height, textPostNote.getHeight(), "Height should match.");

        // Assert: Ensure the ViewModel is updated correctly
        assertEquals(text, viewModel.getText(), "View model text should match.");
        assertEquals(x, viewModel.getX(), "View model X position should match.");
        assertEquals(y, viewModel.getY(), "View model Y position should match.");
        assertEquals(color, viewModel.getColor(), "View model color should match.");
    }
}
