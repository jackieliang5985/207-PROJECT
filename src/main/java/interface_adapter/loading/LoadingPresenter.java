package interface_adapter.loading;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_title.LoadedInState;
import interface_adapter.change_title.LoadedInViewModel;
import use_case.loading.LoadingOutputBoundary;
import use_case.loading.LoadingOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoadingPresenter implements LoadingOutputBoundary {

    private final LoadingViewModel loadingViewModel;
    private final LoadedInViewModel loadedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoadingPresenter(ViewManagerModel viewManagerModel,
                            LoadedInViewModel loadedInViewModel,
                            LoadingViewModel loadingViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loadedInViewModel = loadedInViewModel;
        this.loadingViewModel = loadingViewModel;
    }

    @Override
    public void prepareSuccessView(LoadingOutputData response) {
        // On success, switch to the logged in view.

        final LoadedInState loadedInState = loadedInViewModel.getState();
        loadedInState.setName(response.getUsername());
        this.loadedInViewModel.setState(loadedInState);
        this.loadedInViewModel.firePropertyChanged();

        this.viewManagerModel.setState(loadedInViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoadingState loadingState = loadingViewModel.getState();
        loadingState.setLoginError(error);
        loadingViewModel.firePropertyChanged();
    }
}
