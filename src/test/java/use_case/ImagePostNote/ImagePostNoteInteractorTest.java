package use_case.ImagePostNote;

import data_access.InMemoryPostNoteDataAccessObject;
import entity.MindMapEntity;
import entity.ImagePostNoteEntity;
import entity.PostNoteEntity;
import interface_adapter.add_Image_PostNote.ImagePostNoteController;
import interface_adapter.add_Image_PostNote.ImagePostNotePresenter;
import interface_adapter.add_Image_PostNote.ImagePostNoteViewModel;
import org.junit.jupiter.api.Test;
import use_case.add_Image_PostNote.ImagePostNoteInputData;
import use_case.add_Image_PostNote.ImagePostNoteInteractor;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

//Empty DAO Tests: You may want to add more edge cases to check for scenarios where the DAO might be empty,
// or an invalid image post note is added.

//Error Handling Tests: Testing how the interactor, presenter, and controller handle invalid inputs
// (such as invalid coordinates, unsupported image URLs, or null values) would be beneficial.

//Concurrency Tests: If multiple threads are allowed to add image post notes,
// testing concurrency would be important to avoid race conditions.

//Persistence Layer Testing: If your application uses an actual database (instead of in-memory storage),
// testing the persistence layer's behavior when interacting with a database would be helpful.

class ImagePostNoteInteractorTest {

    @Test
    void testAddImagePostNote() {
        // Create the in-memory DAO and presenter
        InMemoryPostNoteDataAccessObject postNoteDAO = new InMemoryPostNoteDataAccessObject();
        ImagePostNoteViewModel viewModel = new ImagePostNoteViewModel();
        ImagePostNotePresenter presenter = new ImagePostNotePresenter(viewModel);

        // Create a MindMapEntity (assuming title and empty postNotes list)
        MindMapEntity mindMapEntity = new MindMapEntity("Test Mind Map", new ArrayList<>());

        // Create the interactor instance
        ImagePostNoteInteractor interactor = new ImagePostNoteInteractor(presenter, postNoteDAO, mindMapEntity);

        // Create the input data (image URL, coordinates, dimensions, and color)
        String imageUrl = "https://images.unsplash.com/photo-1591729651527-4d09a51b1e2c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2Nzg0NTF8MHx8fHNlYXJjaHw1fHxkYWR8ZW58MHx8fHwxNzMyNjU2OTY3fDA&ixlib=rb-4.0.3&q=80&w=400";
        int x = 50;
        int y = 50;
        int width = 400;
        int height = 300;
        Color color = Color.ORANGE;

        // Create the input data object
        ImagePostNoteInputData inputData = new ImagePostNoteInputData(imageUrl, x, y, width, height, color);

        // Add the image post note using the interactor
        interactor.addImagePostNote(inputData);

        // Get all post notes from the DAO
        List<PostNoteEntity> allPostNotes = postNoteDAO.getAllPostNotes();

        // Assert that the post note has been added to the DAO
        assertEquals(1, allPostNotes.size(), "Expected 1 post note in the DAO.");

        // Cast the PostNoteEntity to ImagePostNoteEntity and check image URL
        PostNoteEntity addedPostNote = allPostNotes.get(0);
        assertTrue(addedPostNote instanceof ImagePostNoteEntity, "Expected post note to be an instance of ImagePostNoteEntity.");

        ImagePostNoteEntity imagePostNote = (ImagePostNoteEntity) addedPostNote;

        // Assert that the added post note matches the expected data
        assertEquals(imageUrl, imagePostNote.getImageUrl(), "Image URL should match.");
        assertEquals(x, imagePostNote.getX(), "X coordinate should match.");
        assertEquals(y, imagePostNote.getY(), "Y coordinate should match.");
        assertEquals(width, imagePostNote.getWidth(), "Width should match.");
        assertEquals(height, imagePostNote.getHeight(), "Height should match.");

        // Assert that the viewModel is updated correctly
        assertEquals(imageUrl, viewModel.getImageUrl(), "View model image URL should match.");
        assertEquals(x, viewModel.getX(), "View model X coordinate should match.");
        assertEquals(y, viewModel.getY(), "View model Y coordinate should match.");
        assertEquals(color, viewModel.getColor(), "View model color should match.");
    }

    @Test
    void testAddImagePostNoteWithNullImageUrl() {
        // Arrange
        String imageUrl = null;  // Simulate a null image URL
        int x = 10, y = 20, width = 300, height = 200;
        Color color = Color.RED;

        InMemoryPostNoteDataAccessObject postNoteDAO = new InMemoryPostNoteDataAccessObject(); // Ensure DAO is created here
        ImagePostNoteViewModel viewModel = new ImagePostNoteViewModel();
        ImagePostNotePresenter presenter = new ImagePostNotePresenter(viewModel);

        ImagePostNoteController controller = new ImagePostNoteController(
                new ImagePostNoteInteractor(presenter, postNoteDAO, new MindMapEntity("Test Mind Map", new ArrayList<>())),
                viewModel
        );

        // Act
        controller.addImagePostNote(imageUrl, x, y, width, height, color);

        // Assert: Make sure the post note is added with a null image URL
        PostNoteEntity addedPostNote = postNoteDAO.getAllPostNotes().get(0);
        assertTrue(addedPostNote instanceof ImagePostNoteEntity, "Expected post note to be an instance of ImagePostNoteEntity.");
        ImagePostNoteEntity imagePostNote = (ImagePostNoteEntity) addedPostNote;
        assertNull(imagePostNote.getImageUrl(), "Image URL should be null.");
    }

    @Test
    void testAddImagePostNoteWithEmptyImageUrl() {
        // Arrange
        String imageUrl = "";  // Simulate an empty image URL
        int x = 10, y = 20, width = 300, height = 200;
        Color color = Color.RED;

        InMemoryPostNoteDataAccessObject postNoteDAO = new InMemoryPostNoteDataAccessObject(); // Ensure DAO is created here
        ImagePostNoteViewModel viewModel = new ImagePostNoteViewModel();
        ImagePostNotePresenter presenter = new ImagePostNotePresenter(viewModel);

        ImagePostNoteController controller = new ImagePostNoteController(
                new ImagePostNoteInteractor(presenter, postNoteDAO, new MindMapEntity("Test Mind Map", new ArrayList<>())),
                viewModel
        );

        // Act
        controller.addImagePostNote(imageUrl, x, y, width, height, color);

        // Assert: Make sure the post note is added with an empty image URL
        PostNoteEntity addedPostNote = postNoteDAO.getAllPostNotes().get(0);
        assertTrue(addedPostNote instanceof ImagePostNoteEntity, "Expected post note to be an instance of ImagePostNoteEntity.");
        ImagePostNoteEntity imagePostNote = (ImagePostNoteEntity) addedPostNote;
        assertEquals("", imagePostNote.getImageUrl(), "Image URL should be an empty string.");
    }
    @Test
    void testAddImagePostNoteWhenDAOIsEmpty() {
        // Arrange
        String imageUrl = "https://example.com/image.jpg";
        int x = 50, y = 100, width = 250, height = 150;
        Color color = Color.YELLOW;

        // Create an in-memory DAO and presenter
        InMemoryPostNoteDataAccessObject postNoteDAO = new InMemoryPostNoteDataAccessObject(); // Instantiate DAO
        ImagePostNoteViewModel viewModel = new ImagePostNoteViewModel();
        ImagePostNotePresenter presenter = new ImagePostNotePresenter(viewModel);

        // Create a MindMapEntity (assuming title and empty postNotes list)
        MindMapEntity mindMapEntity = new MindMapEntity("Test Mind Map", new ArrayList<>());

        // Create the interactor instance
        ImagePostNoteInteractor interactor = new ImagePostNoteInteractor(presenter, postNoteDAO, mindMapEntity);

        // Create the controller and bind to the interactor
        ImagePostNoteController controller = new ImagePostNoteController(interactor, viewModel);

        // Act: Add the image post note using the controller
        controller.addImagePostNote(imageUrl, x, y, width, height, color);

        // Assert: Make sure the post note is added to the empty DAO
        assertEquals(1, postNoteDAO.getAllPostNotes().size(), "Post note should be added to the DAO.");

        // Get the added post note
        PostNoteEntity addedPostNote = postNoteDAO.getAllPostNotes().get(0);

        // Assert: Check if it's an instance of ImagePostNoteEntity
        assertTrue(addedPostNote instanceof ImagePostNoteEntity, "Expected post note to be an instance of ImagePostNoteEntity.");

        ImagePostNoteEntity imagePostNote = (ImagePostNoteEntity) addedPostNote;

        // Assert: Ensure the values match
        assertEquals(imageUrl, imagePostNote.getImageUrl(), "Image URL should match.");
        assertEquals(x, imagePostNote.getX(), "X position should match.");
        assertEquals(y, imagePostNote.getY(), "Y position should match.");
        assertEquals(width, imagePostNote.getWidth(), "Width should match.");
        assertEquals(height, imagePostNote.getHeight(), "Height should match.");

        // Assert: Ensure the ViewModel is updated correctly
        assertEquals(imageUrl, viewModel.getImageUrl(), "View model image URL should match.");
        assertEquals(x, viewModel.getX(), "View model X position should match.");
        assertEquals(y, viewModel.getY(), "View model Y position should match.");
        assertEquals(color, viewModel.getColor(), "View model color should match.");
    }

    @Test
    void testAddMultipleImagePostNotesToDAO() {
        // Arrange
        // Create the in-memory DAO and presenter
        InMemoryPostNoteDataAccessObject postNoteDAO = new InMemoryPostNoteDataAccessObject();
        ImagePostNoteViewModel viewModel = new ImagePostNoteViewModel();
        ImagePostNotePresenter presenter = new ImagePostNotePresenter(viewModel);

        // Create a MindMapEntity (assuming title and empty postNotes list)
        MindMapEntity mindMapEntity = new MindMapEntity("Test Mind Map", new ArrayList<>());

        // Create the interactor instance
        ImagePostNoteInteractor interactor = new ImagePostNoteInteractor(presenter, postNoteDAO, mindMapEntity);

        // Create the controller and bind to the interactor
        ImagePostNoteController controller = new ImagePostNoteController(interactor, viewModel);

        // Define some test data (image URLs, coordinates, dimensions, and color)
        String[] imageUrls = {
                "https://example.com/image1.jpg",
                "https://example.com/image2.jpg",
                "https://example.com/image3.jpg"
        };
        int[] xCoordinates = {50, 100, 150};
        int[] yCoordinates = {100, 200, 300};
        int[] widths = {250, 300, 350};
        int[] heights = {150, 200, 250};
        Color color = Color.YELLOW;

        // Act: Add multiple image post notes using the controller
        for (int i = 0; i < imageUrls.length; i++) {
            controller.addImagePostNote(imageUrls[i], xCoordinates[i], yCoordinates[i], widths[i], heights[i], color);
        }

        // Assert: Verify that the DAO contains the correct number of post notes
        List<PostNoteEntity> allPostNotes = postNoteDAO.getAllPostNotes();
        assertEquals(3, allPostNotes.size(), "DAO should contain 3 post notes.");

        // Assert: Verify each post note in the DAO
        for (int i = 0; i < allPostNotes.size(); i++) {
            PostNoteEntity addedPostNote = allPostNotes.get(i);
            assertTrue(addedPostNote instanceof ImagePostNoteEntity, "Expected post note to be an instance of ImagePostNoteEntity.");

            ImagePostNoteEntity imagePostNote = (ImagePostNoteEntity) addedPostNote;

            // Assert that each post note matches the expected data
            assertEquals(imageUrls[i], imagePostNote.getImageUrl(), "Image URL should match.");
            assertEquals(xCoordinates[i], imagePostNote.getX(), "X position should match.");
            assertEquals(yCoordinates[i], imagePostNote.getY(), "Y position should match.");
            assertEquals(widths[i], imagePostNote.getWidth(), "Width should match.");
            assertEquals(heights[i], imagePostNote.getHeight(), "Height should match.");
        }

        // Assert: Ensure the ViewModel is updated correctly with the last post note
        assertEquals(imageUrls[2], viewModel.getImageUrl(), "View model image URL should match.");
        assertEquals(xCoordinates[2], viewModel.getX(), "View model X position should match.");
        assertEquals(yCoordinates[2], viewModel.getY(), "View model Y position should match.");
        assertEquals(color, viewModel.getColor(), "View model color should match.");
    }
    @Test
    void testPresenterUpdatesViewModel() {
        // Arrange
        String imageUrl = "https://example.com/image.jpg";
        int x = 50, y = 100, width = 250, height = 150;
        Color color = Color.YELLOW;

        // Create an in-memory DAO and MindMapEntity
        InMemoryPostNoteDataAccessObject postNoteDAO = new InMemoryPostNoteDataAccessObject();
        MindMapEntity mindMapEntity = new MindMapEntity("Test Mind Map", new ArrayList<>());

        // Create the view model and presenter
        ImagePostNoteViewModel viewModel = new ImagePostNoteViewModel();
        ImagePostNotePresenter presenter = new ImagePostNotePresenter(viewModel);

        // Create the interactor and controller
        ImagePostNoteInteractor interactor = new ImagePostNoteInteractor(presenter, postNoteDAO, mindMapEntity);
        ImagePostNoteController controller = new ImagePostNoteController(interactor, viewModel);

        // Act: Add image post note
        controller.addImagePostNote(imageUrl, x, y, width, height, color);

        // Assert: Ensure the view model is updated with the correct values
        assertEquals(imageUrl, viewModel.getImageUrl(), "Image URL should match the view model.");
        assertEquals(x, viewModel.getX(), "X position should match the view model.");
        assertEquals(y, viewModel.getY(), "Y position should match the view model.");
        assertEquals(width, viewModel.getWidth(), "Width should match the view model.");
        assertEquals(height, viewModel.getHeight(), "Height should match the view model.");
        assertEquals(color, viewModel.getColor(), "Color should match the view model.");
    }

}

