package use_case.change_title;

/**
 * The output boundary interface for the Change Title use case.
 */
public interface ChangeTitleOutputBoundary {
    void prepareSuccessView(ChangeTitleOutputData outputData);
    void prepareFailView(String error);
}
