package use_case.change_title;

public class ChangeTitleInteractor implements ChangeTitleInputBoundary {
    private final ChangeTitleOutputBoundary outputBoundary;

    public ChangeTitleInteractor(ChangeTitleOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(ChangeTitleInputData inputData) {
        String currentTitle = inputData.getCurrentTitle();
        String newTitle = inputData.getNewTitle();

        // Ensure the title is different and valid
        if (currentTitle != null && !currentTitle.trim().equals(newTitle)) {
            // Update the title in LoggedInState (this part might be missing)
            ChangeTitleOutputData outputData = new ChangeTitleOutputData(newTitle);
            outputBoundary.prepareSuccessView(outputData);  // Notify the presenter
        } else {
            // Handle failure (if titles are the same or invalid)
            outputBoundary.prepareFailView("The new title is the same as the current one.");
        }
    }
}