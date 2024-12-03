package interface_adapter.create_MindMap;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.change_title.LoadedInState;
import interface_adapter.loading.LoadingViewModel;
import use_case.create_MindMap.MindMapOutputBoundary;
import use_case.create_MindMap.MindMapOutputData;

public class MindMapPresenter implements MindMapOutputBoundary {

    private final MindMapViewModel mindMapViewModel;
    private final LoadingViewModel loadingViewModel;
    private final ViewManagerModel viewManagerModel;
    private ViewModel<LoadedInState> loggedInViewModel;

    public MindMapPresenter(ViewManagerModel viewManagerModel,
                            MindMapViewModel mindMapViewModel,
                            LoadingViewModel loadingViewModel,
                            ViewModel<LoadedInState> loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.mindMapViewModel = mindMapViewModel;
        this.loadingViewModel = loadingViewModel;
        this.loggedInViewModel = loggedInViewModel; // Pass it in the constructor
    }

    @Override
    public void prepareSuccessView(MindMapOutputData response) {
        final LoadedInState loadedInState = loggedInViewModel.getState();
        loadedInState.setName(response.getName());
        System.out.println("Updated name in presenter: " + loadedInState.getName()); // Debugging output

        // Trigger property change event for the initial name
        loggedInViewModel.setState(loadedInState);
        loggedInViewModel.firePropertyChanged("name"); // Fire the event for the name change

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
            viewManagerModel.setState(loadingViewModel.getViewName());
            viewManagerModel.firePropertyChanged();
        } else {
            System.err.println("Error: ViewManagerModel is null.");
        }
    }
}
