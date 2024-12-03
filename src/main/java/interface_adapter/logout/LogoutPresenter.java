package interface_adapter.logout;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_title.LoadedInState;
import interface_adapter.change_title.LoadedInViewModel;
import interface_adapter.loading.LoadingState;
import interface_adapter.loading.LoadingViewModel;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

/**
 * The Presenter for the Logout Use Case.
 */
public class LogoutPresenter implements LogoutOutputBoundary {

    private LoadedInViewModel loadedInViewModel;
    private ViewManagerModel viewManagerModel;
    private LoadingViewModel loadingViewModel;

    public LogoutPresenter(ViewManagerModel viewManagerModel,
                          LoadedInViewModel loadedInViewModel,
                           LoadingViewModel loadingViewModel) {
        this.loadedInViewModel = loadedInViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loadingViewModel = loadingViewModel;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData response) {
        // We need to switch to the login view, which should have
        // an empty username and password.

        // We also need to set the username in the LoggedInState to
        // the empty string.

        // 1. get the LoggedInState out of the appropriate View Model,
        final LoadedInState loggedOutState = loadedInViewModel.getState();
        // 2. set the username in the state to the empty string
        loggedOutState.setName("");
        // 3. set the state in the LoggedInViewModel to the updated state
        this.loadedInViewModel.setState(loggedOutState);
        // 4. firePropertyChanged so that the View that is listening is updated.
        this.viewManagerModel.setState(loadingViewModel.getViewName());
        // dont know if this line above is useful
        viewManagerModel.firePropertyChanged();

        // 5. get the LoginState out of the appropriate View Model,
        final LoadingState loadingState = loadingViewModel.getState();
        // 6. set the username and password in the state to the empty string
        loadingState.setName("");
        loadingState.setDescription("");
        // 7. set the state in the LoginViewModel to the updated state
        this.loadingViewModel.setState(loadingState);
        // 8. firePropertyChanged so that the View that is listening is updated.
        viewManagerModel.firePropertyChanged();
        // This code tells the View Manager to switch to the LoginView.
        this.viewManagerModel.setState(loadingViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        // No need to add code here. We'll assume that logout can't fail.
        // Thought question: is this a reasonable assumption?
    }
}
