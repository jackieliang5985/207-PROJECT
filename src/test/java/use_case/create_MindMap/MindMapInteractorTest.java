package use_case.create_MindMap;

import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MindMapInteractorTest {
    @Test
    void successTest() {
        MindMapInputData inputData = new MindMapInputData("My map", "new mind map");
        MindMapUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // This creates a successPresenter that tests whether the test case is as we expect.
        MindMapOutputBoundary successPresenter = new MindMapOutputBoundary() {
            @Override
            public void prepareSuccessView(MindMapOutputData user) {
                // 2 things to check: the output data is correct, and the user has been created in the DAO.
                assertEquals("My map", user.getName());
                assertTrue(userRepository.existsByName("My map"));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        MindMapInputBoundary interactor = new MindMapInteractor(userRepository, successPresenter, new CommonUserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureMapNameExistsTest() {
        MindMapInputData inputData = new MindMapInputData("My map", "hi");
        MindMapUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // Add Paul to the repo so that when we check later they already exist
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("My map", "new mind map");
        userRepository.save(user);

        // This creates a presenter that tests whether the test case is as we expect.
        MindMapOutputBoundary failurePresenter = new MindMapOutputBoundary() {
            @Override
            public void prepareSuccessView(MindMapOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Name already exists.", error);
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        MindMapInputBoundary interactor = new MindMapInteractor(userRepository, failurePresenter, new CommonUserFactory());
        interactor.execute(inputData);
    }
}
