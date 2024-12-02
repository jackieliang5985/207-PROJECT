package use_case.delete;
import data_access.InMemoryPostNoteDataAccessObject;
import entity.MindMapEntity;
import entity.TextPostNoteEntity;
import org.junit.jupiter.api.Test;
import use_case.delete_note.*;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

class DeleteInteractorTest {

    @Test
    void successTest() {
        // Input data to delete the post note
        DeletePostNoteInputData inputData = new DeletePostNoteInputData(300, 400, 150, 150);
        InMemoryPostNoteDataAccessObject noteRepository = new InMemoryPostNoteDataAccessObject();

        // Create and add a post note to the repository
        MindMapEntity mindMap = new MindMapEntity("map", new ArrayList<>());
        TextPostNoteEntity note = new TextPostNoteEntity(300, 400, 150, 150, Color.yellow, mindMap);
        noteRepository.addPostNote(note);

        // Success presenter to verify the test case
        DeletePostNoteOutputBoundary successPresenter = new DeletePostNoteOutputBoundary() {
            @Override
            public void presentDeletePostNoteResult(DeletePostNoteOutputData outputData) {
                // Check that the deletion was successful
                assertTrue(outputData.isSuccess());
                assertEquals("Post Note deleted successfully.", outputData.getMessage());
            }
        };

        // Create the interactor and execute the use case
        DeletePostNoteInputBoundary interactor = new DeletePostNoteInteractor(successPresenter, noteRepository, mindMap);
        interactor.execute(inputData);
    }

    @Test
    void failureNoteNotFoundTest() {
        // Input data to delete a post note that does not exist
        DeletePostNoteInputData inputData = new DeletePostNoteInputData(400, 400, 150, 150);
        InMemoryPostNoteDataAccessObject noteRepository = new InMemoryPostNoteDataAccessObject();

        // Create and add a post note to the repository
        MindMapEntity mindMap = new MindMapEntity("map", new ArrayList<>());
        TextPostNoteEntity note = new TextPostNoteEntity(300, 400, 150, 150, Color.yellow, mindMap);
        noteRepository.addPostNote(note);

        // Failure presenter to verify the test case
        DeletePostNoteOutputBoundary failurePresenter = new DeletePostNoteOutputBoundary() {
            @Override
            public void presentDeletePostNoteResult(DeletePostNoteOutputData outputData) {
                // Check that the deletion failed and the correct error message is returned
                assertFalse(outputData.isSuccess());
                assertEquals("Post Note not found or could not be deleted.", outputData.getMessage());
            }
        };

        // Create the interactor and execute the use case
        DeletePostNoteInputBoundary interactor = new DeletePostNoteInteractor(failurePresenter, noteRepository, mindMap);
        interactor.execute(inputData);
    }
}
