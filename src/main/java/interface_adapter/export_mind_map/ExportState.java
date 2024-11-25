package interface_adapter.export_mind_map;

/**
 * Represents the state of an export operation, including details about the
 * file path, error message (if any), and export status.
 * This class provides getters and setters to manage and retrieve export-related information.
 */
public class ExportState {
    private String filePath;
    private String errorMessage;
    private String exportStatus;

    /**
     * Retrieves the file path of the exported file.
     *
     * @return the file path where the exported file is saved
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the file path for the exported file.
     *
     * @param filePath the file path where the exported file will be saved
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Retrieves the error message associated with the export operation.
     *
     * @return the error message, or null if there was no error
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message for the export operation.
     *
     * @param errorMessage the error message describing the failure
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Retrieves the current status of the export operation.
     *
     * @return the export status (e.g., "Success", "Failed")
     */
    public String getExportStatus() {
        return exportStatus;
    }

    /**
     * Sets the current status of the export operation.
     *
     * @param exportStatus the status of the export process (e.g., "Success", "Failed")
     */
    public void setExportStatus(String exportStatus) {
        this.exportStatus = exportStatus;
    }
}

