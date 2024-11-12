package use_case.loading;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface LoadingInputBoundary {

    /**
     * Executes the login use case.
     * @param loadingInputData the input data
     */
    void execute(LoadingInputData loadingInputData);
}
