package interface_adapter.change_title;

import use_case.change_title.ChangeTitleOutputBoundary;
import use_case.change_title.ChangeTitleOutputData;

public class ChangeTitlePresenter implements ChangeTitleOutputBoundary {
    private final LoadedInViewModel loadedInViewModel;

    public ChangeTitlePresenter(LoadedInViewModel loadedInViewModel) {
        this.loadedInViewModel = loadedInViewModel;
    }

    @Override
    public void prepareSuccessView(ChangeTitleOutputData outputData) {
        // Update the name in the ViewModel's state (ensure property change is fired)
        LoadedInState state = loadedInViewModel.getState();
        state.setName(outputData.getNewTitle());  // Update name to the new title
        loadedInViewModel.setState(state);        // Set the new state
        loadedInViewModel.firePropertyChanged();  // Notify the view
    }

    @Override
    public void prepareFailView(String error) {
        // Handle failure case
        System.out.println("Error: " + error);  // Log the error for debugging
    }
}

