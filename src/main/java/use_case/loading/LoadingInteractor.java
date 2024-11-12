package use_case.loading;

import entity.User;

/**
 * The Login Interactor.
 */
public class LoadingInteractor implements LoadingInputBoundary {
    private final LoadingUserDataAccessInterface userDataAccessObject;
    private final LoadingOutputBoundary loginPresenter;

    public LoadingInteractor(LoadingUserDataAccessInterface userDataAccessInterface,
                             LoadingOutputBoundary loadingOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loadingOutputBoundary;
    }

    @Override
    public void execute(LoadingInputData loadingInputData) {
        final String username = loadingInputData.getUsername();
        final String password = loadingInputData.getPassword();
        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        }
        else {
            final String pwd = userDataAccessObject.get(username).getPassword();
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {

                final User user = userDataAccessObject.get(loadingInputData.getUsername());

                userDataAccessObject.setCurrentUsername(user.getName());
                final LoadingOutputData loadingOutputData = new LoadingOutputData(user.getName(), false);
                loginPresenter.prepareSuccessView(loadingOutputData);
            }
        }
    }
}
