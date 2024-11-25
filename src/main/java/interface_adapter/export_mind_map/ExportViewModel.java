package interface_adapter.export_mind_map;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import use_case.export_mind_map.ExportOutputBoundary;
import use_case.export_mind_map.ExportOutputData;

public class ExportViewModel implements ExportOutputBoundary {
    private final ExportState exportState;
    private final PropertyChangeSupport support;

    public ExportViewModel(ExportState exportState) {
        this.exportState = exportState;
        this.support = new PropertyChangeSupport(this);
    }

    @Override
    public void prepareSuccessView(ExportOutputData outputData) {
        // Update the export status and file path
        setExportStatus("Success");
        setFilePath(outputData.getFilePath());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Update the export status and error message
        setExportStatus("Failure");
        setErrorMessage(errorMessage);
    }

    // Getter and Setter Methods for ExportState
    public String getFilePath() {
        return exportState.getFilePath();
    }

    public void setFilePath(String filePath) {
        String oldPath = exportState.getFilePath();
        exportState.setFilePath(filePath);
        support.firePropertyChange("filePath", oldPath, filePath);
    }

    public String getExportStatus() {
        return exportState.getExportStatus();
    }

    public void setExportStatus(String exportStatus) {
        String oldStatus = exportState.getExportStatus();
        exportState.setExportStatus(exportStatus);
        support.firePropertyChange("exportStatus", oldStatus, exportStatus);
    }

    public String getErrorMessage() {
        return exportState.getErrorMessage();
    }

    public void setErrorMessage(String errorMessage) {
        String oldMessage = exportState.getErrorMessage();
        exportState.setErrorMessage(errorMessage);
        support.firePropertyChange("errorMessage", oldMessage, errorMessage);
    }

    // Property Change Listener Methods
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}