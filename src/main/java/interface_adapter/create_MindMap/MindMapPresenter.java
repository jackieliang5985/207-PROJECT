package interface_adapter.create_MindMap;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.loading.LoadingViewModel;
import use_case.create_MindMap.MindMapOutputBoundary;
import use_case.create_MindMap.MindMapOutputData;


/**
 * The Presenter for the Signup Use Case.
 */
public class MindMapPresenter implements MindMapOutputBoundary {

    private final MindMapViewModel mindMapViewModel;
    private final LoadingViewModel loadingViewModel;
    private final ViewManagerModel viewManagerModel;
    private ViewModel<LoggedInState> loggedInViewModel;

    public MindMapPresenter(ViewManagerModel viewManagerModel,
                            MindMapViewModel mindMapViewModel,
                            LoadingViewModel loadingViewModel,
                            ViewModel<LoggedInState> loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.mindMapViewModel = mindMapViewModel;
        this.loadingViewModel = loadingViewModel;
        this.loggedInViewModel = loggedInViewModel; // Pass it in the constructor
    }

    @Override
    public void prepareSuccessView(MindMapOutputData response) {
        // Debugging: print out response to ensure it's correct
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setName(response.getName());
        this.loggedInViewModel.setState(loggedInState);
        this.loggedInViewModel.firePropertyChanged();

        this.viewManagerModel.setState(loggedInViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final MindMapState mindMapState = mindMapViewModel.getState();
        mindMapState.setNameError(error);
        mindMapViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoginView() {
        if (viewManagerModel != null) {
            // Assuming viewManagerModel is responsible for switching views
            viewManagerModel.setState(loadingViewModel.getViewName());
            viewManagerModel.firePropertyChanged();
        }
        else {
            System.err.println("Error: ViewManagerModel is null.");
        }
    }
}
