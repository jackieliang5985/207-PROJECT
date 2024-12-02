package use_case.change_color;

import data_access.InMemoryPostNoteDataAccessObject;
import entity.*;

import java.awt.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangeColorInteractorTest {

    @Test
    void successTest() {
        ChangeColorInputData inputData = new ChangeColorInputData(300, 400, 150, 150, Color.CYAN);
        InMemoryPostNoteDataAccessObject noteRepository = new InMemoryPostNoteDataAccessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        MindMapEntity mindMap = new MindMapEntity("map", new ArrayList<>());
        TextPostNoteEntity note = new TextPostNoteEntity(300, 400, 150, 150,
                Color.YELLOW, mindMap);
        noteRepository.addPostNote(note);

        // This creates a successPresenter that tests whether the test case is as we expect.
        ChangeColorOutputBoundary successPresenter = new ChangeColorOutputBoundary() {
            @Override
            public void prepareSuccessView(ChangeColorOutputData note) {
                // Check that the color of the post-it note has been changed to the color given in the input data.
                assertEquals(Color.CYAN.toString(), note.getColor().toString());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

        };

        ChangeColorInputBoundary interactor = new ChangeColorInteractor(noteRepository, successPresenter, noteRepository);
        interactor.execute(inputData);

    }

    @Test
    void failureColorIsNullTest() {
        ChangeColorInputData inputData = new ChangeColorInputData(300, 400, 150, 150, null);
        InMemoryPostNoteDataAccessObject noteRepository = new InMemoryPostNoteDataAccessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        MindMapEntity mindMap = new MindMapEntity("map", new ArrayList<>());
        TextPostNoteEntity note = new TextPostNoteEntity(300, 400, 150, 150,
                Color.YELLOW, mindMap);
        noteRepository.addPostNote(note);

        // This creates a failurePresenter that tests whether the test case is as we expect.
        ChangeColorOutputBoundary failurePresenter = new ChangeColorOutputBoundary() {
            @Override
            public void prepareSuccessView(ChangeColorOutputData note) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Color was not changed.", error);
            }

        };

        ChangeColorInputBoundary interactor = new ChangeColorInteractor(noteRepository, failurePresenter, noteRepository);
        interactor.execute(inputData);

    }

    @Test
    void failureNoteNotFoundTest() {
        ChangeColorInputData inputData = new ChangeColorInputData(400, 400, 150, 150, Color.CYAN);
        InMemoryPostNoteDataAccessObject noteRepository = new InMemoryPostNoteDataAccessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        MindMapEntity mindMap = new MindMapEntity("map", new ArrayList<>());
        TextPostNoteEntity note = new TextPostNoteEntity(300, 400, 150, 150,
                Color.YELLOW, mindMap);
        noteRepository.addPostNote(note);

        // This creates a failurePresenter that tests whether the test case is as we expect.
        ChangeColorOutputBoundary failurePresenter = new ChangeColorOutputBoundary() {
            @Override
            public void prepareSuccessView(ChangeColorOutputData note) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Post-it note not found.", error);
            }

        };

        ChangeColorInputBoundary interactor = new ChangeColorInteractor(noteRepository, failurePresenter, noteRepository);
        interactor.execute(inputData);

    }
}
