//package use_case.signup;
//
//import data_access.InMemoryUserDataAccessObject;
//import entity.CommonUserFactory;
//import entity.User;
//import entity.UserFactory;
//import org.junit.jupiter.api.Test;
//import use_case.create_MindMap.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class SignupInteractorTest {
//
//    @Test
//    void successTest() {
//        MindMapInputData inputData = new MindMapInputData("Paul", "password", "password");
//        MindMapUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
//
//        // This creates a successPresenter that tests whether the test case is as we expect.
//        MindMapOutputBoundary successPresenter = new MindMapOutputBoundary() {
//            @Override
//            public void prepareSuccessView(MindMapOutputData user) {
//                // 2 things to check: the output data is correct, and the user has been created in the DAO.
//                assertEquals("Paul", user.getUsername());
//                assertTrue(userRepository.existsByName("Paul"));
//            }
//
//            @Override
//            public void prepareFailView(String error) {
//                fail("Use case failure is unexpected.");
//            }
//
//            @Override
//            public void switchToLoginView() {
//                // This is expected
//            }
//        };
//
//        MindMapInputBoundary interactor = new MindMapInteractor(userRepository, successPresenter, new CommonUserFactory());
//        interactor.execute(inputData);
//    }
//
//    @Test
//    void failurePasswordMismatchTest() {
//        MindMapInputData inputData = new MindMapInputData("Paul", "password", "wrong");
//        MindMapUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
//
//        // This creates a presenter that tests whether the test case is as we expect.
//        MindMapOutputBoundary failurePresenter = new MindMapOutputBoundary() {
//            @Override
//            public void prepareSuccessView(MindMapOutputData user) {
//                // this should never be reached since the test case should fail
//                fail("Use case success is unexpected.");
//            }
//
//            @Override
//            public void prepareFailView(String error) {
//                assertEquals("Passwords don't match.", error);
//            }
//
//            @Override
//            public void switchToLoginView() {
//                // This is expected
//            }
//        };
//
//        MindMapInputBoundary interactor = new MindMapInteractor(userRepository, failurePresenter, new CommonUserFactory());
//        interactor.execute(inputData);
//    }
//
//    @Test
//    void failureUserExistsTest() {
//        MindMapInputData inputData = new MindMapInputData("Paul", "password", "wrong");
//        MindMapUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
//
//        // Add Paul to the repo so that when we check later they already exist
//        UserFactory factory = new CommonUserFactory();
//        User user = factory.create("Paul", "pwd");
//        userRepository.save(user);
//
//        // This creates a presenter that tests whether the test case is as we expect.
//        MindMapOutputBoundary failurePresenter = new MindMapOutputBoundary() {
//            @Override
//            public void prepareSuccessView(MindMapOutputData user) {
//                // this should never be reached since the test case should fail
//                fail("Use case success is unexpected.");
//            }
//
//            @Override
//            public void prepareFailView(String error) {
//                assertEquals("User already exists.", error);
//            }
//
//            @Override
//            public void switchToLoginView() {
//                // This is expected
//            }
//        };
//
//        MindMapInputBoundary interactor = new MindMapInteractor(userRepository, failurePresenter, new CommonUserFactory());
//        interactor.execute(inputData);
//    }
//}