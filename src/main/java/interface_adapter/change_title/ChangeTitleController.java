package interface_adapter.change_title;

import use_case.change_title.ChangeTitleInputBoundary;
import use_case.change_title.ChangeTitleInputData;

public class ChangeTitleController {
    private final ChangeTitleInputBoundary changeTitleInputBoundary;
    private final LoadedInViewModel loadedInViewModel;

    public ChangeTitleController(ChangeTitleInputBoundary changeTitleInputBoundary, LoadedInViewModel loadedInViewModel) {
        this.changeTitleInputBoundary = changeTitleInputBoundary;
        this.loadedInViewModel = loadedInViewModel;
    }

    public void execute(String newTitle, String currentTitle) {
        // Prepare the input data and execute the business logic
        ChangeTitleInputData inputData = new ChangeTitleInputData(newTitle, currentTitle);
        changeTitleInputBoundary.execute(inputData);

        // After executing, update the ViewModel with the new title
        loadedInViewModel.setName(newTitle);  // This should trigger the property change in the ViewModel
    }
}

