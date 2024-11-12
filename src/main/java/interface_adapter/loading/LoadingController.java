package interface_adapter.loading;

import use_case.loading.LoadingInputBoundary;
import use_case.loading.LoadingInputData;

/**
 * The controller for the Login Use Case.
 */
public class LoadingController {

    private final LoadingInputBoundary loginUseCaseInteractor;

    public LoadingController(LoadingInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    /**
     * Executes the Login Use Case.
     * @param username the username of the user logging in
     * @param password the password of the user logging in
     */
    public void execute(String username, String password) {
        final LoadingInputData loadingInputData = new LoadingInputData(
                username, password);

        loginUseCaseInteractor.execute(loadingInputData);
    }
}
