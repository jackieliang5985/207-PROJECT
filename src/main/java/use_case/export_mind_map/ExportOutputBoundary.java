package use_case.export_mind_map;

public interface ExportOutputBoundary {
    /**
     * Prepares the success view for the Export use case.
     * @param outputData the success output data
     */
    void prepareSuccessView(ExportOutputData outputData);

    /**
     * Prepares the failure view for the Export use case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
