package interface_adapter.export_mind_map;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ExportViewModel {
    private final ExportState exportState;
    private final PropertyChangeSupport support; // For notifying observers

    public ExportViewModel(ExportState exportState) {
        this.exportState = exportState;
        this.support = new PropertyChangeSupport(this); // Initialize the support object
    }

    /**
     * Get the current file path from the state.
     * @return the file path where the image was saved.
     */
    public String getFilePath() {
        return exportState.getFilePath();
    }

    /**
     * Update the file path in the state and notify listeners of the change.
     * @param filePath the new file path where the image was saved.
     */
    public void setFilePath(String filePath) {
        String oldPath = exportState.getFilePath();
        exportState.setFilePath(filePath);
        support.firePropertyChange("filePath", oldPath, filePath); // Notify observers of the change
    }

    /**
     * Get the current export status from the state.
     * @return the export status ("Success", "Failure", etc.).
     */
    public String getExportStatus() {
        return exportState.getExportStatus();
    }

    /**
     * Update the export status in the state and notify listeners of the change.
     * @param exportStatus the new status of the export process.
     */
    public void setExportStatus(String exportStatus) {
        String oldStatus = exportState.getExportStatus();
        exportState.setExportStatus(exportStatus);
        support.firePropertyChange("exportStatus", oldStatus, exportStatus); // Notify observers of the change
    }

    /**
     * Get the current error message from the state.
     * @return the error message if the export failed.
     */
    public String getErrorMessage() {
        return exportState.getErrorMessage();
    }

    /**
     * Update the error message in the state and notify listeners of the change.
     * @param errorMessage the new error message for the export process.
     */
    public void setErrorMessage(String errorMessage) {
        String oldMessage = exportState.getErrorMessage();
        exportState.setErrorMessage(errorMessage);
        support.firePropertyChange("errorMessage", oldMessage, errorMessage); // Notify observers of the change
    }

    /**
     * Add a listener to be notified of property changes.
     * @param listener the PropertyChangeListener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Remove a listener from being notified of property changes.
     * @param listener the PropertyChangeListener to be removed.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}