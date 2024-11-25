package interface_adapter.export_mind_map;

import use_case.export_mind_map.ExportInputBoundary;
import use_case.export_mind_map.ExportInputData;
import use_case.export_mind_map.ExportInteractor;

import javax.swing.*;
import java.util.Arrays;

public class ExportController {
    private final ExportInteractor exportInteractor;

    public ExportController(ExportInteractor exportInteractor) {
        this.exportInteractor = exportInteractor; // Initialize with ExportInteractor
    }

    /**
     * Handles the user's export command and invokes the interactor.
     * @param panel the JPanel to export
     */
    public void handleExportCommand(JPanel panel) {
        // Supported formats
        final ExportInputData inputData = new ExportInputData(panel);

        // Call the interactor's execute method
        exportInteractor.execute(inputData); // Use exportInteractor directly
    }
}