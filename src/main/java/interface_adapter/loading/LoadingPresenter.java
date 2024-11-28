package interface_adapter.loading;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_title.LoggedInState;
import interface_adapter.change_title.LoggedInViewModel;
import use_case.loading.LoadingOutputBoundary;
import use_case.loading.LoadingOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoadingPresenter implements LoadingOutputBoundary {

    private final LoadingViewModel loadingViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoadingPresenter(ViewManagerModel viewManagerModel,
                            LoggedInViewModel loggedInViewModel,
                            LoadingViewModel loadingViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loadingViewModel = loadingViewModel;
    }

    @Override
    public void prepareSuccessView(LoadingOutputData response) {
        // On success, switch to the logged in view.

        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setName(response.getUsername());
        this.loggedInViewModel.setState(loggedInState);
        this.loggedInViewModel.firePropertyChanged();

        this.viewManagerModel.setState(loggedInViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoadingState loadingState = loadingViewModel.getState();
        loadingState.setLoginError(error);
        loadingViewModel.firePropertyChanged();
    }
}
