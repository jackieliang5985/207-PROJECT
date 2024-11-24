package use_case.export_mind_map;

public interface ExportInputBoundary {
    /**
     * Executes the export use case.
     * @param inputData the input data for the export (screenshot, file path, and file extension)
     */
    void execute(ExportInputData inputData);
}