package use_case.change_color;

/**
 * The output boundary for the Change Color Use Case.
 */
public interface ChangeColorOutputBoundary {
    /**
     * Prepares the success view for the Change Color Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ChangeColorOutputData outputData);

    /**
     * Prepares the failure view for the Change Color Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
