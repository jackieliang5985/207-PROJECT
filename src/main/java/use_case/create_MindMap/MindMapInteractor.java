package use_case.create_MindMap;

import entity.User;
import entity.UserFactory;

/**
 * The Signup Interactor.
 */
public class MindMapInteractor implements MindMapInputBoundary {
    private final MindMapUserDataAccessInterface userDataAccessObject;
    private final MindMapOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public MindMapInteractor(MindMapUserDataAccessInterface signupDataAccessInterface,
                             MindMapOutputBoundary mindMapOutputBoundary,
                             UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = mindMapOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(MindMapInputData mindMapInputData) {
        if (userDataAccessObject.existsByName(mindMapInputData.getName())) {
            userPresenter.prepareFailView("Name already exists.");
        }

        else {
            final User user = userFactory.create(mindMapInputData.getName(), mindMapInputData.getDescription());
            userDataAccessObject.save(user);

            final MindMapOutputData mindMapOutputData = new MindMapOutputData(user.getName(), false);
            userPresenter.prepareSuccessView(mindMapOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
