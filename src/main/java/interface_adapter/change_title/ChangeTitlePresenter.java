package interface_adapter.change_title;

import use_case.change_title.ChangeTitleOutputBoundary;
import use_case.change_title.ChangeTitleOutputData;

public class ChangeTitlePresenter implements ChangeTitleOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;

    public ChangeTitlePresenter(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(ChangeTitleOutputData outputData) {
        // Update the logged-in view with the new title
        loggedInViewModel.firePropertyChanged("title");
    }

    @Override
    public void prepareFailView(String error) {
        // Handle failure case (e.g., title is invalid)
        // You could show an error message in the UI if needed
    }
}
