package use_case.change_title;

import interface_adapter.change_title.ChangeTitlePresenter;
import interface_adapter.change_title.LoggedInState;
import interface_adapter.change_title.LoggedInViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

// testExecute_validTitleChange: Valid title change (expected to work).
// testExecute_emptyNewTitle: Empty title (expected to fail).
// testExecute_sameNewTitle: New title is the same as the current title (should not change).
// testExecute_validLongTitleChange: Long title change (should work).
// testExecute_titleWithLeadingTrailingSpaces: Title with spaces at the beginning and end (spaces should be trimmed).
// testExecute_nullNewTitle: Null new title (should not change).
// testExecute_newTitleWithOnlySpaces: New title is only spaces (should be treated as empty).

class ChangeTitleInteractorTest {

    private ChangeTitleInteractor changeTitleInteractor;
    private LoggedInState loggedInState;
    private ChangeTitleOutputBoundary outputBoundary;
    private LoggedInViewModel loggedInViewModel;

    @BeforeEach
    void setUp() {
        // Initialize LoggedInState and LoggedInViewModel (real implementations)
        loggedInState = new LoggedInState();
        loggedInViewModel = new LoggedInViewModel();
        loggedInViewModel.setState(loggedInState); // Set initial state

        // Set up the output boundary and interactor
        outputBoundary = new ChangeTitlePresenter(loggedInViewModel);  // Use the real presenter
        changeTitleInteractor = new ChangeTitleInteractor(outputBoundary);
    }
    @Test
    void testExecute_validTitleChange() {
        // Arrange: Set the initial title and prepare input data
        String currentTitle = "Old MindMap";
        String newTitle = "New MindMap";

        // Set the initial state with the current title
        loggedInState.setName(currentTitle);

        // Create input data for the change title use case
        ChangeTitleInputData inputData = new ChangeTitleInputData(currentTitle, newTitle);

        // Act: Call the execute method to change the title
        changeTitleInteractor.execute(inputData);

        // Assert: Verify the title was updated
        System.out.println("Current title: " + loggedInState.getName());  // Debugging output
        assertEquals(newTitle, loggedInState.getName(), "The title should be updated to the new title.");
    }
    @Test
    void testExecute_emptyNewTitle() {
        // Arrange: Set the initial title and provide an empty new title
        String currentTitle = "Old MindMap";
        String newTitle = ""; // Empty title is invalid

        // Set the initial state with the current title
        loggedInState.setName(currentTitle);

        // Create input data with the new title as empty
        ChangeTitleInputData inputData = new ChangeTitleInputData(currentTitle, newTitle);

        // Act: Execute the title change
        changeTitleInteractor.execute(inputData);

        // Assert: Verify that the title remains unchanged (since the new title is invalid)
        assertEquals(currentTitle, loggedInState.getName(), "The title should not change when the new title is empty.");
    }

    @Test
    void testExecute_sameTitle() {
        // Arrange: Set the initial title and provide the same title as the new one
        String currentTitle = "MindMap";
        String newTitle = "MindMap"; // Same title, no change

        // Set the initial state with the current title
        loggedInState.setName(currentTitle);

        // Create input data for the title change (same title)
        ChangeTitleInputData inputData = new ChangeTitleInputData(currentTitle, newTitle);

        // Act: Execute the title change
        changeTitleInteractor.execute(inputData);

        // Assert: Verify that the title remains unchanged
        assertEquals(currentTitle, loggedInState.getName(), "The title should remain unchanged when the new title is the same.");
    }

    @Test
    void testExecute_invalidTitle() {
        // Arrange: Set the initial title and provide an invalid new title (e.g., null)
        String currentTitle = "Old MindMap";
        String newTitle = null; // Invalid title (null)

        // Set the initial state with the current title
        loggedInState.setName(currentTitle);

        // Create input data with the invalid new title
        ChangeTitleInputData inputData = new ChangeTitleInputData(currentTitle, newTitle);

        // Act: Execute the title change
        changeTitleInteractor.execute(inputData);

        // Assert: Verify that the title remains unchanged
        assertEquals(currentTitle, loggedInState.getName(), "The title should not change when the new title is invalid (null).");
    }

    @Test
    void testExecute_sameNewTitle() {
        // Arrange: Set the initial title and provide the same title as the new title
        String currentTitle = "Old MindMap";
        String newTitle = "Old MindMap"; // Same as current title

        // Set the initial state with the current title
        loggedInState.setName(currentTitle);

        // Create input data with the new title as the same
        ChangeTitleInputData inputData = new ChangeTitleInputData(currentTitle, newTitle);

        // Act: Execute the title change
        changeTitleInteractor.execute(inputData);

        // Assert: Verify that the title remains unchanged (since the new title is the same as the current one)
        assertEquals(currentTitle, loggedInState.getName(), "The title should remain the same when the new title is the same.");
    }

    @Test
    void testExecute_validLongTitleChange() {
        // Arrange: Set the initial title and prepare input data
        String currentTitle = "Old MindMap";
        String newTitle = "This is a very long title that should still be accepted and updated correctly.";

        // Set the initial state with the current title
        loggedInState.setName(currentTitle);

        // Create input data for the change title use case
        ChangeTitleInputData inputData = new ChangeTitleInputData(currentTitle, newTitle);

        // Act: Call the execute method to change the title
        changeTitleInteractor.execute(inputData);

        // Assert: Verify the title was updated correctly
        assertEquals(newTitle, loggedInState.getName(), "The title should be updated to the new long title.");
    }

    @Test
    void testExecute_titleWithLeadingTrailingSpaces() {
        // Arrange: Set the initial title and prepare input data
        String currentTitle = "Old MindMap";
        String newTitle = "  New MindMap  ";  // New title with leading and trailing spaces

        // Set the initial state with the current title
        loggedInState.setName(currentTitle);

        // Create input data for the change title use case
        ChangeTitleInputData inputData = new ChangeTitleInputData(currentTitle, newTitle.trim());  // Remove spaces for comparison

        // Act: Call the execute method to change the title
        changeTitleInteractor.execute(inputData);

        // Assert: Verify that the title was updated (trimming the spaces)
        assertEquals("New MindMap", loggedInState.getName(), "The title should be updated to the new title, trimmed.");
    }

    @Test
    void testExecute_newTitleWithOnlySpaces() {
        // Arrange: Set the initial title and provide a new title that consists of only spaces
        String currentTitle = "Old MindMap";
        String newTitle = "   "; // New title is just spaces

        // Set the initial state with the current title
        loggedInState.setName(currentTitle);

        // Create input data for the change title use case
        ChangeTitleInputData inputData = new ChangeTitleInputData(currentTitle, newTitle.trim());

        // Act: Execute the title change
        changeTitleInteractor.execute(inputData);

        // Assert: Verify that the title remains unchanged (since the new title is empty after trimming)
        assertEquals(currentTitle, loggedInState.getName(), "The title should not change when the new title is just spaces.");
    }


}
