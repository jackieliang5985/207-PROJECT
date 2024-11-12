package use_case.create_MindMap;

/**
 * Input Boundary for actions which are related to signing up.
 */
public interface MindMapInputBoundary {

    /**
     * Executes the signup use case.
     * @param mindMapInputData the input data
     */
    void execute(MindMapInputData mindMapInputData);

    /**
     * Executes the switch to login view use case.
     */
    void switchToLoginView();
}
