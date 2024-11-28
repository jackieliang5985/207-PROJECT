package use_case.change_title;

public class ChangeTitleInteractor implements ChangeTitleInputBoundary {
    private final ChangeTitleOutputBoundary outputBoundary;

    public ChangeTitleInteractor(ChangeTitleOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(ChangeTitleInputData inputData) {
        // Perform logic to change the title, e.g., validate the new title
        String newTitle = inputData.getNewTitle();
        String currentTitle = inputData.getCurrentTitle();

        // Assuming the title change is successful
        ChangeTitleOutputData outputData = new ChangeTitleOutputData(newTitle);
        outputBoundary.prepareSuccessView(outputData);
    }
}

