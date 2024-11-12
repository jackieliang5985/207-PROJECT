package use_case.create_MindMap;

/**
 * The output boundary for the Signup Use Case.
 */
public interface MindMapOutputBoundary {

    /**
     * Prepares the success view for the Signup Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(MindMapOutputData outputData);

    /**
     * Prepares the failure view for the Signup Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the Login View.
     */
    void switchToLoginView();
}
