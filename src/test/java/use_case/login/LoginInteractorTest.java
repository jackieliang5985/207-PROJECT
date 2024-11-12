package use_case.login;

import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;
import use_case.loading.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    @Test
    void successTest() {
        LoadingInputData inputData = new LoadingInputData("Paul", "password");
        LoadingUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);

        // This creates a successPresenter that tests whether the test case is as we expect.
        LoadingOutputBoundary successPresenter = new LoadingOutputBoundary() {
            @Override
            public void prepareSuccessView(LoadingOutputData user) {
                assertEquals("Paul", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoadingInputBoundary interactor = new LoadingInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void successUserLoggedInTest() {
        LoadingInputData inputData = new LoadingInputData("Paul", "password");
        LoadingUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);

        // This creates a successPresenter that tests whether the test case is as we expect.
        LoadingOutputBoundary successPresenter = new LoadingOutputBoundary() {
            @Override
            public void prepareSuccessView(LoadingOutputData user) {
                assertEquals("Paul", userRepository.getCurrentUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoadingInputBoundary interactor = new LoadingInteractor(userRepository, successPresenter);
        assertEquals(null, userRepository.getCurrentUsername());

        interactor.execute(inputData);
    }

    @Test
    void failurePasswordMismatchTest() {
        LoadingInputData inputData = new LoadingInputData("Paul", "wrong");
        LoadingUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // For this failure test, we need to add Paul to the data access repository before we log in, and
        // the passwords should not match.
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);

        // This creates a presenter that tests whether the test case is as we expect.
        LoadingOutputBoundary failurePresenter = new LoadingOutputBoundary() {
            @Override
            public void prepareSuccessView(LoadingOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for \"Paul\".", error);
            }
        };

        LoadingInputBoundary interactor = new LoadingInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureUserDoesNotExistTest() {
        LoadingInputData inputData = new LoadingInputData("Paul", "password");
        LoadingUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // Add Paul to the repo so that when we check later they already exist

        // This creates a presenter that tests whether the test case is as we expect.
        LoadingOutputBoundary failurePresenter = new LoadingOutputBoundary() {
            @Override
            public void prepareSuccessView(LoadingOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Paul: Account does not exist.", error);
            }
        };

        LoadingInputBoundary interactor = new LoadingInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }
}